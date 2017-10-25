### RAP可视化接口管理工具使用
#### 一、问题一[img/问题一.png]
```
  修改文件 workspace/myWorkspace.vm
  搜索     "base" : "$consts.DOMAIN_URL$!projectContext"
  修改为   "base" : "127.0.0.1:8080"
```

#### 二、问题二[img/问题二.png]
```
  修改文件 tcom/const.inc.vm
  搜索    #set($projectContext = "$!link.getContextPath()")
  修改为  #set($projectContext = "127.0.0.1")
```

#### 三、配置根路径
```
  修改文件 tester/pageTester.vm
  搜索    var RAP_ROOT = '$consts.DOMAIN_URL$!projectContext/mockjs/$!projectId';
  修改为  var RAP_ROOT = '127.0.0.1:8080/mockjs/$!projectId';
```

#### 四、问题四[demo/seajs-plugin.html插件请求不成功]
```
  修改文件 stat/js/util/mock.plugin.js
  搜索    var ROOT = "$!consts.DOMAIN_URL";;
  修改为  var ROOT = "127.0.0.1";;
```

#### 五、修改模板
```
  修改文件 tcom/template.rap.vm
  可修改首页相关信息
```
#### 六、修改session失效时间
```
  修改文件 WEB-INF/web.xml
  <session-config>
      <session-timeout>60</session-timeout><!-- 有效时间 -->
  </session-config>
```

#### 七、同意修改根路径
```
  修改文件 com.taobao.rigel.rap.common.config.SystemConstant
  参数  DOMAIN_URL  domainURL

```
