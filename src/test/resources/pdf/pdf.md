### PDF常用类解释

#### PdfReader (com.itextpdf.text.pdf.PdfReader)
```
    1.创建
    PdfReader reader = new PdfReader(final String filename) // filename：文件路径
    2.获取PDF总页数
    int numberOfPages = reader.getNumberOfPages(); //获取PDF模板总页数
```
#### Document (com.itextpdf.text.Document)
```
    1.创建
    Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
    document.open();
    document.close();
    2.文件写入什么位置
    PdfWriter.getInstance(document, new FileOutputStream("G:\\target.pdf"));
```

#### PdfStamper (com.itextpdf.text.pdf.PdfStamper)
```
    1.创建
    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
    PdfStamper stamper = new PdfStamper(reader, baos)  //reader：PDF阅读器  baos：文件输入流
    2.输出文档时，设置文档是否可编辑
    stamper.setFormFlattening(true); // 设置文档不可以编辑，这句不能少
    3.获取PDF某页内容
    PdfContentByte content = stamper.getOverContent(pageIndex); //pageIndex：PDF页
```

#### AcroFields (com.itextpdf.text.pdf.AcroFields)
```
    1.创建
    AcroFields form = stamper.getAcroFields();  // stamper:标准的PDF模板
    2.设置支持中文
    form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
    3.替换模板中的数据
    form.setField("name", "value");  //name：表单的字段名称， value：替换的值
```

#### PdfPTable (com.itextpdf.text.pdf.PdfPTable)
```
    1.创建
    PdfPTable table = new PdfPTable(columns);  //columns：表格多少列
    2.方法
    //table.setHorizontalAlignment(1); //设置表格的对齐方式 参数参考：Element.ALIGN_CENTER
    //table.setHeaderRows(1);  //表示第几行作为表格头
    //table.setFooterRows(2);  //表示第几行作为表格尾
    //table.setSplitLate(true);  // 表示单元格是否跨页显示
    //table.setSplitRows(true);  //表示行是否跨页显示
    //table.setHeaderRows();  //标题栏 设置头几行为表头
    //table.setFooterRows();  //页脚栏
    //table.setTotalWidth(400); //设置表总高度
    //table.setTotalWidth(float[] columns); //设置每列宽度
    //table.setWidthPercentage();  //设置表格占的宽度，百分比
    //Rectangle rect = new Rectangle(523, 770);  //矩形
    //table.setSpacingBefore();  //距前
    //table.setSpacingAfter();  //距后
    //table.setLockedWidth();  //固定宽度
    //table.setComplete(false);  //自动将表格元素输出到document中
    //table.deleteBodyRows();  //删掉这些已经放进去的元素
    //table.setSkipFirstHeader(false);  //每隔100行，产生一个表头  true:设置了表头不显示
    //table.addCell(cell);  //添加一个单元格
    //table.addCell("value");  //添加一个单元格 value:单元格的值
```

#### PdfPCell (com.itextpdf.text.pdf.PdfPCell)
```
    1.创建
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
    PdfPTable.addCell(cell);
```