package top.guyi.amazon.mws.response.impl.orders

import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenResponse
import top.guyi.amazon.mws.response.SuperResponseHandler

/**
 * Created by 古逸 on 2017-07-28.
 */
class ListOrdersByNextTokenHandler extends SuperResponseHandler<ListOrdersByNextTokenResponse,ListOrdersByNextTokenRequest>{

    @Override
    String getXML(ListOrdersByNextTokenResponse response) {
        return response.toXML()
    }

    String nextToken

    @Override
    List<EntityData> pattern() {
        return null
    }

    @Override
    void over() {
        this.nextToken = this.response().listOrdersByNextTokenResult.nextToken
    }
}
