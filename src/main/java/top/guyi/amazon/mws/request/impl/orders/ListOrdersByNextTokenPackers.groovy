package top.guyi.amazon.mws.request.impl.orders

import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.OrdersClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.orders.ListOrdersByNextTokenHandler

/**
 * Created by 古逸 on 2017-07-28.
 */
class ListOrdersByNextTokenPackers  extends SuperRequestPackers<ListOrdersByNextTokenPackers,ListOrdersByNextTokenRequest,ListOrdersByNextTokenHandler> {

    private ListOrdersByNextTokenRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return OrdersClient.class
    }

    @Override
    String method() {
        return 'listOrdersByNextToken'
    }

    ListOrdersByNextTokenPackers must(String nextToken){
        this.amazonRequest().nextToken = nextToken
        return this
    }

    @Override
    ListOrdersByNextTokenRequest amazonRequest() {
        if(request == null){
            request = new ListOrdersByNextTokenRequest()
            request.sellerId = this.config().current().sellerId()
        }
        return request
    }
}
