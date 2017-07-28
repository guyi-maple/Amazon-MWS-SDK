package top.guyi.amazon.mws.client.impl

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersConfig
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.conf.AmazonConfigProvider

/**
 * Created by 古逸 on 2017-07-28.
 */
class OrdersClient implements SuperAmazonClient<MarketplaceWebServiceOrdersConfig,MarketplaceWebServiceOrdersClient>{

    @Override
    MarketplaceWebServiceOrdersClient create(AmazonConfigProvider provider) {
        MarketplaceWebServiceOrdersConfig config = this.newConfig(provider)
        return this.createByConfig(provider,config)
    }

    @Override
    MarketplaceWebServiceOrdersClient createByConfig(AmazonConfigProvider provider, MarketplaceWebServiceOrdersConfig config) {
        MarketplaceWebServiceOrdersClient client = new MarketplaceWebServiceOrdersClient(provider.accessKey(),provider.secretKey(),config)
        return client
    }

    @Override
    MarketplaceWebServiceOrdersConfig newConfig(AmazonConfigProvider provider) {
        MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig()
        config.serviceURL = provider.server()
        return config
    }
}
