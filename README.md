# es-crud
基于java实现elasticsearch的增删改查
##pom.xml引入jar
<dependency>
    		<groupId>org.elasticsearch.client</groupId>
    		<artifactId>transport</artifactId>
    		<version>5.2.2</version>
</dependency>
<dependency>
    		<groupId>org.apache.logging.log4j</groupId>
    		<artifactId>log4j-api</artifactId>
    		<version>2.7</version>
</dependency>
<dependency>
    		<groupId>org.apache.logging.log4j</groupId>
    		<artifactId>log4j-core</artifactId>
    		<version>2.7</version>
</dependency>
##配置log4j
在log4j2.properties文件中输入以下内容
appender.console.type = Console
appender.console.name = console
appender.console.layout.type = PatternLayout

rootLogger.level = info
rootLogger.appenderRef.console.ref = console
##最重要
首先我是在本地windows装的elasticsearch的，开箱即用，这里提供一个安装包，也可以去官网下
链接：https://pan.baidu.com/s/1ygCeb4YOCrcYI4nqF5IsQw 
提取码：8t0y 
下载解压后，只需要进入es的bin目录点击elasticsearch.bat即可启动es,从日志看es绑定端口是9300
然后就可以去启动CoderCRUDApp的main方法进行curd操作了。
