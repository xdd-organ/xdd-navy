### docker
#### docker 安装

```
    
```


#### docker 镜像命令

```
    查看镜像
        docker images 
    搜索镜像
        docker search mysql 
    下载镜像
        docker pull tomcat:8 
        docker pull 镜像名[:tag版本]
    删除镜像
        docker rmi -f 镜像id  
```


#### docker 容器命令

```
    新建容器并启动
        docker run 镜像id
    列出所有运行的容器
        docker ps
    删除指定容器
        docker rm 容器id
    启动容器
        docker start 容器id
    重启容器
        docker restart 容器id
    停止当前正在运行的容器
        docker stop 容器id
    强制停止当前容器
        docker kill 容器id
    docker run [可选参数] image | docker container run [可选参数] image
        --name="Name"   # 容器名字 tomcat01 tomcat02 用来区分容器
        -d   # 后台方式运行
        -it   # 使用交互方式运行，进入容器查看内容
        -p    # 指定容器的端口   -p 8080(宿主机):8080(容器)   
        -P(大写)     # 随机指定端口
    测试、启动并进入容器
        docker run -it centos /bin/bash   # run it centos启动新建一个centos容器，/bin/bash给一个默认控制台
    容器启动中进入
        docker exec -it mysql /bin/bash  # 进入mysql（容器名）容器
    
    
    
```



