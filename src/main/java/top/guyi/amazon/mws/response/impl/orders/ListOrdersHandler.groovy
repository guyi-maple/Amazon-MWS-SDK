package top.guyi.amazon.mws.response.impl.orders

import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse
import top.guyi.amazon.mws.response.SuperResponseHandler

/**
 * Created by 古逸 on 2017-07-28.
 */
class ListOrdersHandler extends SuperResponseHandler<ListOrdersResponse,ListOrdersRequest>{

    @Override
    String getXML(ListOrdersResponse response) {
        return response.toXML()
    }

    String nextToken

    @Override
    List<EntityData> pattern() {
        return null
    }

    @Override
    void over() {
        this.nextToken = this.response().listOrdersResult.nextToken
    }
}
