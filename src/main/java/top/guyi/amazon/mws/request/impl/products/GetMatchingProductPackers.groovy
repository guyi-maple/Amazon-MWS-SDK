package top.guyi.amazon.mws.request.impl.products

import com.amazonaws.mws.model.IdList
import com.amazonservices.mws.products.model.ASINListType
import com.amazonservices.mws.products.model.GetMatchingProductRequest
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ProductsClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.report.GetMatchingProductHandler

/**
 * Created by 古逸 on 2017-05-26.
 */
class GetMatchingProductPackers extends SuperRequestPackers<GetMatchingProductPackers,GetMatchingProductRequest,GetMatchingProductHandler>{

    private GetMatchingProductRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return ProductsClient.class
    }

    @Override
    String method() {
        return 'getMatchingProduct'
    }

    GetMatchingProductPackers must(List<String> aSINList){
        ASINListType list = new ASINListType()
        list.ASIN = aSINList
        this.amazonRequest().ASINList = list
        return this
    }

    GetMatchingProductPackers must(String asin){
        this.must([asin])
        return this
    }

    @Override
    GetMatchingProductRequest amazonRequest() {
        if(request == null){
            request = new GetMatchingProductRequest()
            request.sellerId = this.config().current().sellerId()
            request.marketplaceId = this.config().current().marketplaceId()
        }
        return request
    }
}
