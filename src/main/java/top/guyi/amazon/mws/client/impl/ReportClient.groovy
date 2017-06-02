package top.guyi.amazon.mws.client.impl

import com.amazonaws.mws.MarketplaceWebServiceClient
import com.amazonaws.mws.MarketplaceWebServiceConfig
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.conf.AmazonConfigProvider

/**
 * Created by 古逸 on 2017-05-24.
 */
class ReportClient implements SuperAmazonClient<MarketplaceWebServiceConfig,MarketplaceWebServiceClient> {

    @Override
    MarketplaceWebServiceClient create(AmazonConfigProvider provider) {
        def config = this.newConfig(provider)
        return this.createByConfig(provider,config)
    }

    MarketplaceWebServiceClient createByConfig(AmazonConfigProvider provider, MarketplaceWebServiceConfig config) {
        MarketplaceWebServiceClient client = new MarketplaceWebServiceClient(provider.accessKey(),provider.secretKey(),'MarketplaceWebService','2016-09-21',config)
        return client
    }

    MarketplaceWebServiceConfig newConfig(AmazonConfigProvider provider) {
        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig()
        config.serviceURL = provider.server()
        return config
    }
}
