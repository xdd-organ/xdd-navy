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
import java.io.FileOutputStream;
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
            //Document d = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
            //PdfWriter.getInstance(d, new FileOutputStream("AddBigTable.pdf"));
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

        Rectangle rectangle = new Rectangle(PageSize.A4); //设置页大小
        stamper.insertPage(3, rectangle); //在指定位置插入新一页
        table.writeSelectedRows(0, -1, 100, 400, stamper.getOverContent(3));//

        table.writeSelectedRows(0, -1, 100, 10, stamper.getOverContent(1));//开始行，结束行，表格x起点，表格y起点
    }

    private PdfPTable createTableHasTitle(Table tableDatum, OffsetTable offsetTable) {
        PdfPTable table = new PdfPTable(tableDatum.getColumns());//设置表列
        table.setHorizontalAlignment(1); //设置表格的对齐方式
        //table.setHeaderRows(1);  //表示第几行作为表格头
        table.setFooterRows(2);  //表示第几行作为表格尾
        table.setSplitLate(true);  // 表示单元格是否跨页显示
        table.setSplitRows(true);  //表示行是否跨页显示
        //table.setHeaderRows();  //标题栏 设置头几行为表头
        //table.setFooterRows();  //页脚栏
        //PdfPTableHeader
//        table.setTotalWidth(400); //设置表总高度
        //table.setWidthPercentage();  //设置表格占的宽度，百分比
        //Rectangle rect = new Rectangle(523, 770);  //矩形
        //table.setSpacingBefore();  //距前
        //table.setSpacingAfter();  //距后
        //table.setLockedWidth();  //固定宽度
        //table.setComplete(false);  //自动将表格元素输出到document中
        //table.deleteBodyRows();  //删掉这些已经放进去的元素
        //table.setSkipFirstHeader(false);  //每隔100行，产生一个表头  true:设置了表头不显示

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
                    Phrase elements = new Phrase(column, new Font(baseFont));
                    //Paragraph elements1 = new Paragraph(column, new Font(baseFont));
                    //elements1.setAlignment(Element.ALIGN_CENTER); //文字居中
                    PdfPCell cell = new PdfPCell(elements);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //设置上下居中
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);  //设置水平居中
                    //cell.setFixedHeight(50); //设置列固定高度
                    //cell.setBackgroundColor();  //设置背景颜色
                    //cell.setColspan();  //合并几列
                    //cell.setRowspan();  //合并几行
                    //Element.ALIGN_CENTER;
                    cell.addElement(elements);
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


    @Test
    public void test2() throws Exception{
        Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("G:\\target.pdf"));    // step3
            document.open();
            // step4
            String[] bogusData = { "M0065920", "SL", "FR86000P", "PCGOLD",      "119000", "96 06", "2001-08-13", "4350", "6011648299",      "FLFLMTGP", "153", "119000.00" };
            int NumColumns = 12;
            PdfPTable datatable = new PdfPTable(NumColumns);
            int headerwidths[] = { 9, 4, 8, 10, 8, 11, 9, 7, 9, 10, 4, 10 };
            // percentage
            datatable.setWidths(headerwidths);
            datatable.setWidthPercentage(100); // percentage
            datatable.getDefaultCell().setPadding(3);
            datatable.getDefaultCell().setBorderWidth(2);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            datatable.addCell("Clock #");
            datatable.addCell("Trans Type");
            datatable.addCell("Cusip");
            datatable.addCell("Long Name");
            datatable.addCell("Quantity");
            datatable.addCell("Fraction Price");
            datatable.addCell("Settle Date");

            datatable.addCell("Portfolio");
            datatable.addCell("ADP Number");
            datatable.addCell("Account ID");
            datatable.addCell("Reg Rep ID");
            datatable.addCell("Amt To Go ");
            datatable.setHeaderRows(1); // this is the end of the table header
            datatable.getDefaultCell().setBorderWidth(1);
            for (int i = 1; i < 750; i++) {
                if (i % 2 == 1) {
                    datatable.getDefaultCell().setGrayFill(0.9f);
                }
                for (int x = 0; x < NumColumns; x++) {
                    datatable.addCell(bogusData[x]);
                }
                if (i % 2 == 1) {
                    datatable.getDefaultCell().setGrayFill(1);
                }
            }
            document.add(datatable);
        } catch (Exception de) {
            de.printStackTrace();
        }
        // step5
        document.close();
    }
}
