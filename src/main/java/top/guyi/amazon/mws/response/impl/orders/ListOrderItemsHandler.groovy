package top.guyi.amazon.mws.response.impl.orders

import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse
import top.guyi.amazon.mws.response.SuperResponseHandler

/**
 * Created by 古逸 on 2017-07-28.
 */
class ListOrderItemsHandler extends SuperResponseHandler<ListOrderItemsResponse,ListOrderItemsRequest>{

    @Override
    String getXML(ListOrderItemsResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return null
    }
}
