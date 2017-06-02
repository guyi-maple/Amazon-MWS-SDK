package top.guyi.amazon.mws

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.guyi.amazon.mws.client.AmazonClientFactory
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ProductsClient
import top.guyi.amazon.mws.client.impl.ReportClient
import top.guyi.amazon.mws.conf.AmazonConfigFactory
import top.guyi.amazon.mws.conf.Impl.DefaultAmazonConfigProvider
import top.guyi.amazon.mws.conf.AmazonConfigProvider

/**
 * Created by 古逸 on 2017-05-24.
 */
@Configuration
@EnableConfigurationProperties(DefaultAmazonConfigProvider.class)
class AmazonMWSAutoConfigure {

    @Bean
    ReportClient reportClient(){
        return new ReportClient()
    }

    @Bean
    ProductsClient productsClient(){
        return new ProductsClient()
    }

    @Bean
    AmazonConfigFactory amazonConfigFactory(List<AmazonConfigProvider> providers){
        AmazonConfigFactory factory = new AmazonConfigFactory()
        factory.providers = providers
        return factory
    }

    @Bean
    AmazonClientFactory amazonClientFactory(List<SuperAmazonClient> clients){
        AmazonClientFactory factory = new AmazonClientFactory()
        factory.clients = clients
        return factory
    }

}
