package top.guyi.amazon.mws.client

import top.guyi.amazon.mws.conf.AmazonConfigProvider

/**
 * Created by 古逸 on 2017-05-24.
 */
interface SuperAmazonClient<C,E> {

    E create(AmazonConfigProvider provider)

    E createByConfig(AmazonConfigProvider provider,C config)

    C newConfig(AmazonConfigProvider provider)
}
