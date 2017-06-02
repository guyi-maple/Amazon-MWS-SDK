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
