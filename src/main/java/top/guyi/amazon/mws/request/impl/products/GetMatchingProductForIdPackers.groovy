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
