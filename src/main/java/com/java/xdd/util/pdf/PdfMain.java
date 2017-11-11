package com.java.xdd.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;
import com.java.xdd.system.domain.SystemLog;
import com.java.xdd.util.pdf.pojo.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/11.
 */
public class PdfMain {

    /**
     * 生成PDF文档
     * @param pdfData PDF数据
     * @param template 模板文件
     * @param outFile PDF输出
     */
    void generatePdf(PdfData pdfData, File template, File outFile) {
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


    private void setTableDatum(PdfStamper stamper, Table tableDatum, OffsetTable offsetTable) {
        PdfPTable table = createTableHasTitle(tableDatum, offsetTable);
        ArrayList<PdfPRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            System.out.print(table.getRowHeight(i));  //获取每列高度
            PdfPRow pdfPRow = rows.get(i);
            System.out.println("==" + pdfPRow.getMaxHeights());
        }
        table.writeSelectedRows(0, -1, 100, 100, stamper.getOverContent(1));//开始行，结束行，表格x起点，表格y起点

        Rectangle rectangle = new Rectangle(PageSize.A4); //设置页大小
        stamper.insertPage(2, rectangle); //在指定位置插入新一页
        table.writeSelectedRows(0, -1, 100, 400, stamper.getOverContent(2));//
    }

    private PdfPTable createTableHasTitle(Table tableDatum, OffsetTable offsetTable) {
        PdfPTable table = new PdfPTable(tableDatum.getColumns());//设置表列
        //table.setHeaderRows();
        //PdfPTableHeader
//        table.setTotalWidth(400); //设置总高度

        try {
            float[] a = new float[]{20,70,100,120};
            table.setTotalWidth(a); //设置每列宽度
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<List<String>> tableData = tableDatum.getTableDatum();
        try {
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            tableData.forEach(row -> {
                row.forEach(column -> {
                    //Phrase elements = new Phrase(column, new Font(baseFont));
                    Paragraph elements1 = new Paragraph(column, new Font(baseFont));
                    elements1.setAlignment(Element.ALIGN_CENTER); //文字居中
                    PdfPCell cell = new PdfPCell(elements1);
                    cell.setVerticalAlignment(1);
                    cell.setFixedHeight(50); //设置列固定高度
                    //Element.ALIGN_CENTER;
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
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            if (formDatum != null && !CollectionUtils.isEmpty(formDatum.getFormDatum())) {
                formDatum.getFormDatum().forEach((k, v) -> {
                    try {form.setField(k, v);} catch (Exception e) {e.printStackTrace();}
                });
            }
            //form.setField("test", "fjsadlf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        File template = new File("E:\\workspace\\idea\\xdd-navy\\src\\test\\java\\com\\java\\xdd\\pdf\\target.pdf");
        File out = new File("G:\\target.pdf");
        PdfData pdfData = new PdfData();

        Form form = new Form();
        Map<String, String> a = new HashMap<>();
        a.put("test", "不好");
        a.put("test2", "nice");
        a.put("test3", "12434");
        a.put("test4", "你猜啊");
        form.setFormDatum(a);

        Table table = new Table();
        List<List<String>> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        c.add("不好");
        c.add("nice");
        c.add("12434");
        c.add("你猜啊");
        b.add(c);
        b.add(c);
        b.add(c);
        List<String> c2 = new ArrayList<>();
        c2.add("不好4");
        c2.add("nice4");
        c2.add("12434");
        c2.add("你猜啊4");
        b.add(c2);
        table.setTableDatum(b);

        pdfData.setFormDatum(form);
        pdfData.setTableDatum(table);

        generatePdf(pdfData, template, out);
    }
}
