### mqtt协议支持点对点推送
```
    1、使用git下载activemq源码 
        git clone https://git-wip-us.apache.org/repos/asf/activemq.git 
    2、git自动下载最新版本的源码，需要下载和你使用的mq程序对应的版本，上一步的网址中有tag一栏，选择对应的版本进去，查看对应的commit所产生的hashcode 
        git checkout <对应版本hashcode> 
    3、自定义消息到topic后推送给订阅者的消息分发策略 
        策略存放路径在项目根路径下的\activemq-broker\src\main\java\org\apache\activemq\broker\region\policy\ 
        在此目录下随便选一个原有的*Policy.java文件，修改此文件内容:PriorityDispatchPolicy.java
    4、重新编译broker 
        在\activemq-broker\下执行mvn package进行打包（打包后的内容应该在target文件夹中） 
        把生成的activemq-broker-version.jar拷贝到使用的mq程序的lib目录下 
    5、修改activemq的配置文件
        <policyEntry topic=">" >
            <dispatchPolicy>
                <!-- 这里的priorityDispatchPolicy是你选对的策略分发类，首字母小写 -->
                <priorityDispatchPolicy />
            </dispatchPolicy>
            <pendingMessageLimitStrategy>
                <constantPendingMessageLimitStrategy limit="1000"/>
            </pendingMessageLimitStrategy>
        </policyEntry>
    
```
