package top.guyi.amazon.mws.conf

/**
 * Created by 古逸 on 2017-05-24.
 */
interface AmazonConfigProvider {

    String version()

    String accessKey()

    String secretKey()

    String server()

    String sellerId()

    String marketplaceId()

    String charset()

}
