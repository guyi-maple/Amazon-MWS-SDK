package top.guyi.amazon.mws.request.impl.products

import com.amazonservices.mws.products.model.ASINListType
import com.amazonservices.mws.products.model.GetMatchingProductRequest
import com.amazonservices.mws.products.model.GetMyPriceForSKURequest
import com.amazonservices.mws.products.model.SellerSKUListType
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ProductsClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.products.GetMyPriceForSKUHandler
import top.guyi.amazon.mws.response.impl.report.GetMatchingProductHandler

/**
 * Created by 古逸 on 2017-05-26.
 */
class GetMyPriceForSKUPackers extends SuperRequestPackers<GetMyPriceForSKUPackers,GetMyPriceForSKURequest,GetMyPriceForSKUHandler>{

    private GetMyPriceForSKURequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return ProductsClient.class
    }

    @Override
    String method() {
        return 'getMyPriceForSKU'
    }

    GetMyPriceForSKUPackers must(List<String> skus){
        SellerSKUListType list = new SellerSKUListType()
        list.sellerSKU = skus
        this.amazonRequest().sellerSKUList = list
        return this
    }

    GetMyPriceForSKUPackers must(String sku){
        this.must([sku])
        return this
    }

    @Override
    GetMyPriceForSKURequest amazonRequest() {
        if(request == null){
            request = new GetMyPriceForSKURequest()
            request.sellerId = this.config().current().sellerId()
            request.marketplaceId = this.config().current().marketplaceId()
        }
        return request
    }
}
