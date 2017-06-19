# amazon-mws-java-sdk
亚马逊MWS服务的Java-SDK封装

# 安装
在pom.xml中添加依赖
``` xml
<dependency>
    <groupId>top.guyi.amazon</groupId>
    <artifactId>Amazon-MWS-SDK</artifactId>
    <version>1.0.0.1</version>
</dependency>
```
依赖放在nexus私服中，并没有上传到中央仓库，所以请在pom.xml中加入仓库配置
``` xml
<repositories>
    <repository>
        <id>guyi-maple-nexus</id>
        <url>http://nexus.guyi-maple.space/nexus/content/groups/public/</url>
    </repository>
</repositories>
```

本项目基于Spring-Boot，依赖添加后会自行进行相关配置

你需要做的只是在application.properties中给出亚马逊MWS的开发者信息

如下：

application.properties
``` properties
guyi.amazon.mws.accessKey = <亚马逊MWS-accessKey>
guyi.amazon.mws.secretKey = <亚马逊MWS-secretKey>
guyi.amazon.mws.server = https://mws.amazonservices.jp #mws端点
guyi.amazon.mws.sellerId = <亚马逊MWS-sellerId>
guyi.amazon.mws.marketplaceId = <亚马逊MWS-marketplaceId>
guyi.amazon.mws.charset = Shift_JIS #编码
```
关于亚马逊MWS端点，请参见 [亚马逊MWS服务端点列表](http://blog.guyi-maple.space/2017/06/19/amazon-mws-server-list/)

编码设置用于数据上传接口中，具体编码信息，请参见 [SubmitFeed Content-Type](http://docs.developer.amazonservices.com/zh_CN/feeds/Feeds_SubmitFeed.html#Feeds_SubmitFeed)

# 开发者信息
如果你需要同时操作多个亚马逊商城，且使用不同的开发者信息，请实现接口 [AmazonConfigProvider](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/conf/AmazonConfigProvider.groovy)

然后将你的实现类放置到Spring容器中

[AmazonConfigProvider](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/conf/AmazonConfigProvider.groovy)接口中的version方法返回当前开发者信息的版本名称
``` java
String version()
```

在使用时可使用此版本名称进行切换

``` java
@Resource
AmazonConfigFactory factory

factory.currentVersion(version) //设置当前线程下使用的开发者信息
factory.current() //获取当前线程下的开发者信息

factory.globalVersion(version) //设置全局开发者信息
factory.globalVersion() //获取全局开发者信息
```

开发者信息版本的优先级为：
Thread(当前线程) -> global(全局) -> default(application.properties中的配置)

# 接口调用

## 新增接口调用流程
接口调用分为三部分，Client -> Request -> Response
分别对应三个接口：
[SuperAmazonClient<C,E>](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/client/SuperAmazonClient.groovy) , [SuperRequestPackers<T,R,P>](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/request/SuperRequestPackers.groovy) , [SuperResponseHandler<P,R>](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/response/SuperResponseHandler.groovy)

### SuperAmazonClient<C,E> Client接口

#### 泛型
泛型 C 对应亚马逊提供的Java客户端中的config类，如
MarketplaceWebServiceProductsConfig，MarketplaceWebServiceConfig等

泛型 E 对应亚马逊提供的Java客户端中的Client类，如
MarketplaceWebServiceProductsClient，MarketplaceWebServiceClient等

#### 新增Client类
需要自己新增Client类时，请实现接口 SuperAmazonClient<C,E>，如下示例实现类
[ProductsClient.groovy](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/client/impl/ProductsClient.groovy)
``` java
package top.guyi.amazon.mws.client.impl

import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.conf.AmazonConfigProvider

/**
 * Created by 古逸 on 2017-05-26.
 */
class ProductsClient implements SuperAmazonClient<MarketplaceWebServiceProductsConfig,MarketplaceWebServiceProductsClient> {

    @Override
    MarketplaceWebServiceProductsClient create(AmazonConfigProvider provider) {
        MarketplaceWebServiceProductsConfig config = this.newConfig(provider)
        return this.createByConfig(provider,config)
    }

    @Override
    MarketplaceWebServiceProductsClient createByConfig(AmazonConfigProvider provider, MarketplaceWebServiceProductsConfig config) {
        MarketplaceWebServiceProductsClient client = new MarketplaceWebServiceProductsClient(provider.accessKey(),provider.secretKey(),config)
        return client
    }

    @Override
    MarketplaceWebServiceProductsConfig newConfig(AmazonConfigProvider provider) {
        MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig()
        config.serviceURL = provider.server()
        return config
    }
}
```

### SuperRequestPackers<T,R,P> Request基类

#### 泛型

泛型 T 表示实现类本身，如实现类名为GetMatchingProductForIdPackers，那么泛型T 就为 GetMatchingProductForIdPackers

泛型 R 对应亚马逊Java客户端中的请求，如
GetMatchingProductForIdRequest，GetLowestOfferListingsForASINRequest，GetLowestOfferListingsForSKURequest等

泛型 P 对应Response类，及响应的处理，有关于Respose类的介绍，请参见下方解释

#### 新增Request类
要新增Request类，请实现基类SuperRequestPackers<T,R,P>

实现类中的 method 方法返回当前请求调用的亚马逊哪一个接口,首字母小写
``` java
String method()
```
亚马逊接口列表，请参见 [亚马逊MWS服务文档](https://developer.amazonservices.com.cn/)

当请求中有必须的参数时，建议添加must方法，如果没有，则不需要

示例代码如下

[GetMatchingProductForIdPackers.groovy](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/request/impl/products/GetMatchingProductForIdPackers.groovy)

``` java
package top.guyi.amazon.mws.request.impl.products

import com.amazonservices.mws.products.model.GetMatchingProductForIdRequest
import com.amazonservices.mws.products.model.IdListType
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ProductsClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.products.GetMatchingProductForIdHandler

/**
 * Created by 古逸 on 2017-06-01.
 */
class GetMatchingProductForIdPackers extends SuperRequestPackers<GetMatchingProductForIdPackers,GetMatchingProductForIdRequest,GetMatchingProductForIdHandler>{

    private GetMatchingProductForIdRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return ProductsClient.class
    }

    @Override
    String method() {
        return 'getMatchingProductForId'
    }

    GetMatchingProductForIdPackers must(String idType,List<String> idList){
        this.amazonRequest().idType = idType
        IdListType list = new IdListType()
        list.id = idList
        this.amazonRequest().idList = list
        return this
    }

    @Override
    GetMatchingProductForIdRequest amazonRequest() {
        if(request == null){
            request = new GetMatchingProductForIdRequest()
            request.sellerId = this.config().current().sellerId()
            request.marketplaceId = this.config().current().marketplaceId()
        }
        return request
    }
}
```

### SuperResponseHandler<P,R> 响应基类

#### 泛型

泛型 P 对应亚马逊Java客户端中的响应，如
GetMatchingProductForIdResponse，GetLowestOfferListingsForASINResponse，GetLowestPricedOffersForASINResponse等

泛型 R 表示Request类，关于Request类请参见上方的解释

#### 新增Response类

新增Response类，需要实现基类 SuperResponseHandler<P,R>

实现类中的 pattern 方法表示处理返回的XML数据，请返回EntityData实体类的数组

EntityData实体类中包含数据处理的正则表达式与Lambda表达式

如果返回 null ，表示不进行处理

示例代码如下

[GetMatchingProductHandler.groovy](https://github.com/guyimaplegithub/Amazon-MWS-SDK/blob/master/src/main/java/top/guyi/amazon/mws/response/impl/products/GetMatchingProductHandler.groovy)

``` java
package top.guyi.amazon.mws.response.impl.report

import com.amazonservices.mws.products.model.GetMatchingProductRequest
import com.amazonservices.mws.products.model.GetMatchingProductResponse
import top.guyi.amazon.mws.response.SuperResponseHandler
import top.guyi.amazon.mws.response.entity.product.Product

/**
 * Created by 古逸 on 2017-05-26.
 */
class GetMatchingProductHandler extends SuperResponseHandler<GetMatchingProductResponse,GetMatchingProductRequest>{

    List<Product> childs = new LinkedList<Product>()

    Product parent

    @Override
    String getXML(GetMatchingProductResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        String xml = this.response().toXML()
        if(xml.contains('ns2:VariationChild')){
            return [
                    EntityData.create(/<MarketplaceId>(.*?)<\/MarketplaceId><ASIN>(.*?)<\/ASIN>/,
                            {line->
                                Product product = new Product()
                                product.marketplaceId = line[1]
                                product.asin = line[2]
                                childs.add(product)
                            })
            ]
        }else if(xml.contains('VariationParent')){
            return [
                    EntityData.create(/<MarketplaceId>(.*?)<\/MarketplaceId><ASIN>(.*?)<\/ASIN>/,
                            {line->
                                parent = new Product()
                                parent.marketplaceId = line[1]
                                parent.asin = line[2]
                            })
            ]
        }
    }
}
```

### 注意
新增的Client、Request、Response实现类，如果要使其生效，都需要放入到Spring的容器中

## 接口调用方式

接口调用时，每一次调用请新建Request对象，此对象没有做单例处理，请尽量将其作为局部变量使用

如：

``` java
GetMatchingProductPackers packers = new GetMatchingProductPackers()
GetMatchingProductHandler response = packers.must('B00S7W8TUQ').invok()
println(response.childs)
println(response.parent)
println(response.getXML())
println(response.response())
```

Request类中集成了自定义参数的配置方式，如下示例
``` java
GetMatchingProductPackers packers = new GetMatchingProductPackers()
packers.customize {request->
    ASINListType list = new ASINListType()
    list.ASIN = ['B00S7W8TUQ']
    request.ASINList = list
}
GetMatchingProductHandler response = packers.invok()
println(response.childs)
println(response.parent)
println(response.getXML())
println(response.response())
```
Request类中集成了自定义开发者信息的配置方式，此配置方式只在当前的Request对象中生效，如下示例
``` java
GetMatchingProductPackers packers = new GetMatchingProductPackers()
packers.customizeAmazonConfig {config->
    config.serviceURL = 'https://mws.amazonservices.jp'
    config.connectionTimeout = 3000
}
GetMatchingProductHandler response = packers.must('B00S7W8TUQ').invok()
println(response.childs)
println(response.parent)
println(response.getXML())
println(response.response())
```

Request类支持Builder设计模式，如下示例

``` java
println(new GetMatchingProductPackers().must('B00S7W8TUQ').invok().parent())
```

``` java
println(new GetMatchingProductPackers()
    .customize({request->
        ASINListType list = new ASINListType()
        list.ASIN = ['B00S7W8TUQ']
        request.ASINList = list
    }).invok().parent())
```

### 在Request中更改开发者信息的版本

当你需要在Request中更改开发者信息版本是，可调用 version 方法

```
GetMatchingProductPackers packers = new GetMatchingProductPackers()
packers.version('Japan')
```

此方式设置的开发者信息版本，优先级为线程，即在当前线程中生效











