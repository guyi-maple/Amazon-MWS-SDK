package top.guyi.amazon.mws.response.impl.products

import com.amazonservices.mws.products.model.GetMyPriceForSKURequest
import com.amazonservices.mws.products.model.GetMyPriceForSKUResponse
import top.guyi.amazon.mws.response.SuperResponseHandler

/**
 * Created by 古逸 on 2017-06-09.
 */
class GetMyPriceForSKUHandler  extends SuperResponseHandler<GetMyPriceForSKUResponse,GetMyPriceForSKURequest> {
    @Override
    String getXML(GetMyPriceForSKUResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return null
    }
}
