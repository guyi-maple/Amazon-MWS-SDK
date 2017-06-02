# amazon-mws-java-sdk
亚马逊MWS服务的Java-SDK封装

项目使用了亚马逊提供的Java客户端，并将其以Maven的形式导入

``` xml
<dependency>
    <groupId>com.amazonservices.mws</groupId>
    <artifactId>report</artifactId>
    <version>2016-09-21</version>
</dependency>

<dependency>
    <groupId>com.amazonservices.mws</groupId>
    <artifactId>products</artifactId>
    <version>2011-10-01</version>
</dependency>
```

此项目存在于Nexus私服中

所以请在pom.xml中添加如下配置

``` xml
<repositories>
    <repository>
        <id>guyi-maple-nexus</id>
        <url>http://nexus.guyi-maple.space/nexus/content/groups/public/</url>
    </repository>
</repositories>
```
那个仓库配置写错没??? 应该没有写错吧................

使用方法，等sdk弄完了再说
