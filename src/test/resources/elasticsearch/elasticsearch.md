### elasticsearch
#### elasticsearch下载

```
    https://thans.cn/mirror/elasticsearch.html 选择自己所需要版本下载
```

#### elasticsearch启动
```
    1、将下载的包解压，示例elasticsearch-6.4.3.tar.gz
        tar -zxvf elasticsearch-6.4.3.tar.gz
    2、启动
         nohup ./elasticsearch &
    
    可能会报错1：max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
        vim /etc/sysctl.conf
        增加配置
            vm.max_map_count=262144
    
    可能会报错2：max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]
        vim /etc/security/limits.conf
        增加配置（如果是user用户，将*更换成user）
            *               soft    nofile          65536
            *               hard    nofile          65536
```

#### elasticsearch同一台机器搭建集群
```
127.0.0.1 node-1配置
    xpack.ml.enabled: false
    cluster.name: recommend-elasticsearch
    network.host: 0.0.0.0
    http.port: 9201
    transport.tcp.port: 9301
    node.name: node-1
    discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301", "127.0.0.1:9302"]
    discovery.zen.minimum_master_nodes: 1
    
    http.cors.enabled: true
    http.cors.allow-origin: "*"
    http.cors.allow-credentials: true
    
127.0.0.1 node-2配置
    xpack.ml.enabled: false
    cluster.name: recommend-elasticsearch
    network.host: 0.0.0.0
    http.port: 9202
    transport.tcp.port: 9302
    node.name: node-2
    discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301", "127.0.0.1:9302"]
    discovery.zen.minimum_master_nodes: 1

    如果node-2复制的是node-1的应用，可能会组建不了集群
        解决办法：删除elsticsearch文件夹下的data文件夹下的节点数据
```








