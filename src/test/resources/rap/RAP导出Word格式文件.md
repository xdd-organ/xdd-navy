### RAP导出Word格式文件(使用接口为：workspace/export.do)
#### 第一步修改模板文件
```
  修改文件 resource/export.vm
  该文件为导出文件模板
  可将非标题的H3标签改为H4标签(兼容Word)
```

#### 修改原生导出.html文件
```
  导出的export.html文件使用文本文档打开
  文件另存为utf-8编码
  修改文件后缀为doc
  使用word代开该文件即可
```

#### 可能需要修改文件
```
  修改文件 com/taobao/rigel/rap/workspace/web/action/struts.xml
  修改方法 export方法
  修改参数 contentType 为 application/msword;charset=utf-8
```

