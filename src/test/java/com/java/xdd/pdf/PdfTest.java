package com.java.xdd.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class PdfTest {

    public static void main(String[] args) throws Exception {
        // 模板文件路径
        String templatePath = "G:\\temp.pdf";
        // 生成的文件路径
        String targetPath = "G:\\target.pdf";
        // 书签名
        String fieldName = "field";
        // 图片路径
        String imagePath = "G:\\image.jpg";

        // 读取模板文件
        InputStream input = new FileInputStream(new File(templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

        PdfName pdfName = new PdfName("test");
        int[] a = new int[]{1, 2, 3};
        PdfObject pdfObject = new PdfArray(a);
        stamper.addViewerPreference(pdfName, pdfObject);


        form.setField("test", "你好");







        /*// 通过域名获取所在页和坐标，左下角为起点
        List<AcroFields.FieldPosition> fieldPositions = form.getFieldPositions(fieldName);
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        // 读图片
        Image image = Image.getInstance(imagePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);*/
        stamper.setFormFlattening(true); // 设置文档不可以编辑，这句不能少
        stamper.close();
        reader.close();
    }

    private void writePageNumbers(File outputFile)//pdf模板
            throws IOException, DocumentException {

        PdfReader reader = new PdfReader(outputFile.getAbsolutePath());
        int numberOfPages = reader.getNumberOfPages(); //获取PDF模板总页数

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, baos);


        //提取pdf中的表单，并设置表单数据
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        form.setField("test", "你好");


        //PDF添加表格数据
        for (int pageIndex = 1; pageIndex <= numberOfPages; ++pageIndex) {
            Chunk chunkPageNumber = new Chunk(String.format(("DocumentTransfertReport.FooterPage"), pageIndex, numberOfPages));
            chunkPageNumber.setFont(new Font());

            Paragraph paragraph = new Paragraph(chunkPageNumber);
            paragraph.setAlignment(Element.ALIGN_RIGHT);

            PdfPCell cell = new PdfPCell(new Paragraph("hehehe", new Font())); //设置内容与字体
            cell.setFixedHeight(50);//设置单元格高度
            cell.setBorder(Rectangle.BOX); //设置哪些位置有边框
            cell.setBorderWidth(0.2f); //设置边框粗细

//            cell.addElement(paragraph);
//            cell.addElement(new Paragraph("hehehe", new Font()));

            PdfPTable table = new PdfPTable(1);//设置表列
            table.setTotalWidth(90);//设置表格总高度
            table.addCell(cell);//添加一个单元格
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            table.addCell(new PdfPCell(new Phrase("this content 支持中文", new Font(baseFont))));
            table.addCell(new PdfPCell(new Phrase("   ", new Font())));
            Font font = new Font();
//            table.addCell("fs34df");
//            table.addCell("131");

            table.writeSelectedRows(0, -1, 100, 100, stamper.getOverContent(pageIndex));//设置表格位置，getOverContent获取PDF内容，参数是页码
        }

        Rectangle rectangle = new Rectangle(PageSize.A4); //页大小
        rectangle.setBackgroundColor(BaseColor.ORANGE); //设置背景颜色
        stamper.insertPage(2, rectangle);//PDF增加新的一页

        PdfContentByte overContent = stamper.getOverContent(1);


        stamper.close();
        reader.close();

        File file = new File("G:\\target2.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileUtils.writeByteArrayToFile(file, baos.toByteArray());
        baos.close();
    }

    @Test
    public void test1() {
        try {
            File file = new File("G:\\temp2.pdf");
            writePageNumbers(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
