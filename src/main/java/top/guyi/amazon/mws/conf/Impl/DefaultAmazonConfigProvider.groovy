package top.guyi.amazon.mws.conf.Impl

import org.springframework.boot.context.properties.ConfigurationProperties
import top.guyi.amazon.mws.conf.AmazonConfigProvider

/**
 * 默认亚马逊配置
 * Created by 古逸 on 2017-05-24.
 */
@ConfigurationProperties(prefix = 'guyi.amazon.mws')
class DefaultAmazonConfigProvider implements AmazonConfigProvider{

    /**
     * guyi.amazon.mws.accessKey
     */
    String accessKey

    /**
     * guyi.amazon.mws.secretKey
     */
    String secretKey

    /**
     * 亚马逊MWS端点服务器地址
     * guyi.amazon.mws.server
     */
    String server

    /**
     * guyi.amazon.mws.sellerId
     */
    String sellerId

    /**
     * guyi.amazon.mws.marketplaceId
     */
    String marketplaceId

    /**
     * 字符编码，作用于上传数据API中
     * guyi.amazon.mws.charset
     */
    String charset

    @Override
    String version() {
        return 'default'
    }

    @Override
    String accessKey() {
        return accessKey
    }

    @Override
    String secretKey() {
        return secretKey
    }

    @Override
    String server() {
        return server
    }

    @Override
    String sellerId() {
        return sellerId
    }

    @Override
    String marketplaceId() {
        return marketplaceId
    }

    @Override
    String charset() {
        return charset
    }

}
