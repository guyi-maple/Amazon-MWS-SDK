package top.guyi.amazon.mws.response.impl.products

import com.amazonservices.mws.products.model.GetMatchingProductForIdRequest
import com.amazonservices.mws.products.model.GetMatchingProductForIdResponse
import top.guyi.amazon.mws.response.SuperResponseHandler

/**
 * Created by 古逸 on 2017-06-01.
 */
class GetMatchingProductForIdHandler extends SuperResponseHandler<GetMatchingProductForIdResponse,GetMatchingProductForIdRequest>{

    @Override
    String getXML(GetMatchingProductForIdResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return null
    }
}
