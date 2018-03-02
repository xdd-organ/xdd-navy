### MYSQL配置

#### 慢查询日志
```
    slow_query_log=off  ##(默认:off)启动停止记录慢查询日志。(on:开,off:关)
    slow_query_log_file = /data/mysql/mysql-slow.log  ## 指定慢查日志的存储路径及文件
    long_query_time=10  ##(默认:10)指定记录慢查日志SQL执行时间的阀值，单位:秒。记录所有符合条件的SQL(查询、修改、回滚)，一般设置：0.001(1毫秒)比较合适
    log_queries_not_using_indexes  ##慢查询日志是否记录未使用索引的SQL
```
#### 查询缓存
```
    query_cache_type=off  ##设置查询缓存是否可用。(on:开,off:关,demand:只有在查询语句中使用sql_cache和sql_no_cache来控制)
    query_cache_size=1024  ##设置查询缓存的内存大小，单位:字节，值要求为1024整数倍
    query_cache_limit  ##设置查询缓存可用存储的最大值
    query_cache_wlock_invalidate  ##设置数据库表被锁后是否返回缓存中的数据
    query_cache_min_res_unit  ##设置查询缓存分配的内存块最小单位
```

#### 用show profile进行sql分析
```
    show profiles;  ##分析当前会话中语句执行的资源消耗情况
    show profile cpu,block io for query 4; ##分析某个SQL语句执行情况，参数中的4是query id
    
    Show profile后面的一些参数：
    all：显示所有的开销信息
    block io：显示块IO相关开销
    context switches： 上下文切换相关开销
    cpu：显示cpu相关开销
    memory：显示内存相关开销
    source：显示和source_function,source_file,source_line相关的开销信息
```

#### mysql数据导出
```
    #需要授权(root权限除外)
    执行语句：
    select _id,_status,_user_designer_id,_user_doctor_id from _order limit 10 into outfile 'G:\\xdd\\a' fields terminated by ',';
    
    into outfile 'G:\\xdd\\a' ## 导出文件位置
    fields terminated by ','  ##返回字段用逗号分隔
    FIELDS TERMINATED BY '字符串'：设置字符串为字段之间的分隔符，可以为单个或多个字符。默认值是“\t”。
    FIELDS ENCLOSED BY '字符'：设置字符来括住字段的值，只能为单个字符。默认情况下不使用任何符号。
    FIELDS OPTIONALLY ENCLOSED BY '字符'：设置字符来括住CHAR、VARCHAR和TEXT等字符型字段。默认情况下不使用任何符号。
    FIELDS ESCAPED BY '字符'：设置转义字符，只能为单个字符。默认值为“\”。
    LINES STARTING BY '字符串'：设置每行数据开头的字符，可以为单个或多个字符。默认情况下不使用任何字符。
    LINES TERMINATED BY '字符串'：设置每行数据结尾的字符，可以为单个或多个字符。默认值是“\n”
```


