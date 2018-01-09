package com.java.xdd.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.java.xdd.util.pdf.pojo.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/11.
 */
public class PdfMain2 {
    /**
     * PDF添加文字
     * @param stamper
     * @param offset
     */
    private void setText(PdfStamper stamper, Offset offset) {
        PdfContentByte overContent = stamper.getOverContent(1);
        try {
            //添加文字
            BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            overContent.beginText();
            overContent.setFontAndSize(font, 10);
            overContent.setTextMatrix(200, 200);
            overContent.showTextAligned(Element.ALIGN_LEFT,"需要添加的文字",10,50,-90);//
            overContent.endText();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setTableDatum(PdfStamper stamper, Table tableDatum, OffsetTable offsetTable) {
        PdfPTable table = createTableHasTitle(tableDatum, offsetTable);
        ArrayList<PdfPRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            System.out.print(table.getRowHeight(i));  //获取每列高度
            PdfPRow pdfPRow = rows.get(i);
            System.out.println("==" + pdfPRow.getMaxHeights());
        }

        table.writeSelectedRows(0, -1, 28, 730, stamper.getOverContent(1));//
    }

    private PdfPTable createTableHasTitle(Table tableDatum, OffsetTable offsetTable) {
        PdfPTable table = new PdfPTable(tableDatum.getColumns());//设置表列

        try {
            float[] a = new float[]{108,108,56,42,230};
            table.setTotalWidth(a); //设置每列宽度
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<List<String>> tableData = tableDatum.getTableDatum();
        try {
            //BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //使用系统字体
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\msyh.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            tableData.forEach(row -> {
                row.forEach(column -> {
                    //Phrase elements = new Phrase(column, new Font(baseFont));
                    Paragraph elements1 = new Paragraph(column, new Font(baseFont, 10, Font.BOLD));
                    elements1.setAlignment(Element.ALIGN_CENTER); //文字居中
                    PdfPCell cell = new PdfPCell(elements1);
                    cell.setUseAscender(true); //配合setVerticalAlignment方法是文字上下居中
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //设置上下居中
                    //cell.setHorizontalAlignment(Element.ALIGN_CENTER);  //设置水平居中
                    //cell.setFixedHeight(25); //设置列固定高度
                    cell.setPaddingTop(5);
                    cell.setPaddingBottom(5);
                    cell.setMinimumHeight(28);
                    cell.addElement(elements1);
                    table.addCell(cell);
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }


    /**
     * 填充表单数据
     * @param stamper
     * @param formDatum
     */
    private void setFormDatum(PdfStamper stamper, Form formDatum) {
        AcroFields form = stamper.getAcroFields();
        try {
            //form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            form.addSubstitutionFont(BaseFont.createFont("C:\\Windows\\Fonts\\simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
            if (formDatum != null && !CollectionUtils.isEmpty(formDatum.getFormDatum())) {
                formDatum.getFormDatum().forEach((k, v) -> {
                    try {form.setField(k, v);} catch (Exception e) {e.printStackTrace();}
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        File template = new File("G:\\template\\template.pdf");
        File out = new File("G:\\template\\template2.pdf");
        PdfData pdfData = new PdfData();

        Form form = new Form();
        Map<String, String> a = new HashMap<>();
        a.put("order_no", "PTY201707081346");
        a.put("operation_date", "2017-07-07");
        a.put("nickname", "张伟");
        a.put("patient_name", "李伟");
        a.put("page", "1/2");
        form.setFormDatum(a);

        Table table = new Table();
        List<List<String>> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        c.add("名称");
        c.add("产品编号");
        c.add("材质");
        c.add("数量");
        c.add("备注");
        b.add(c);
        List<String> c2 = new ArrayList<>();
        c2.add("上颌PLA");
        c2.add("PTY201701021126-1SG107-001");
        c2.add("PLA");
        c2.add("1");
        c2.add("无");
        b.add(c2);
        List<String> c3 = new ArrayList<>();
        c3.add("R1 连接杆");
        c3.add("PTY201701021126-1SG107-001");
        c3.add("进口树脂");
        c3.add("1");
        c3.add("无");
        b.add(c3);
        table.setTableDatum(b);

        pdfData.setFormDatum(form);
        pdfData.setTableDatum(table);


        generatePdf2(pdfData, template, out);
    }

    void generatePdf2(PdfData pdfData, File template, File outFile) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            reader = new PdfReader(template.getAbsolutePath());
            stamper = new PdfStamper(reader, baos);
            //填充表单数据
            setFormDatum(stamper, pdfData.getFormDatum());

            //填充表格数据
            OffsetTable offsetTable = new OffsetTable();
            setTableDatum(stamper, pdfData.getTableDatum(), offsetTable);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            Offset offset = new Offset();
            //添加文字
            //setText(stamper, offset);

            //添加图片
            setImg(stamper, offset);
            setImg2(stamper, offset);
            setImg3(stamper, offset);
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            FileUtils.writeByteArrayToFile(outFile, baos.toByteArray());

            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*if (reader != null) reader.close();
            if (stamper != null) {
                try {
                    //stamper.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    private void setImg(PdfStamper stamper, Offset offset) {
        try {
            PdfContentByte overContent = stamper.getOverContent(1);
            Image image = Image.getInstance("G:\\qwe.jpg");
            image.setAbsolutePosition(28,450); //设置图片在PDF位置
            image.scaleToFit(175, 175);  //设置图片显示大小
            overContent.addImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setImg2(PdfStamper stamper, Offset offset) {
        try {
            PdfContentByte overContent = stamper.getOverContent(1);
            Image image = Image.getInstance("G:\\qwe.jpg");
            image.setAbsolutePosition(213,450); //设置图片在PDF位置
            image.scaleToFit(175, 175);  //设置图片显示大小
            overContent.addImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setImg3(PdfStamper stamper, Offset offset) {
        try {
            PdfContentByte overContent = stamper.getOverContent(1);
            Image image = Image.getInstance("G:\\qwe.jpg");
            image.setAbsolutePosition(396,450); //设置图片在PDF位置
            image.scaleToFit(175, 175);  //设置图片显示大小
            overContent.addImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
