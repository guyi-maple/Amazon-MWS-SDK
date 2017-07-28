package top.guyi.amazon.mws.request.impl.orders

import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.OrdersClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.orders.ListOrderItemsHandler

/**
 * Created by 古逸 on 2017-07-28.
 */
class ListOrderItemsPackers extends SuperRequestPackers<ListOrderItemsPackers,ListOrderItemsRequest,ListOrderItemsHandler>{

    private ListOrderItemsRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return OrdersClient.class
    }

    @Override
    String method() {
        return 'listOrderItems'
    }

    ListOrderItemsPackers must(String amazonOrderId){
        this.amazonRequest().amazonOrderId = amazonOrderId
        return this
    }

    @Override
    ListOrderItemsRequest amazonRequest() {
        if(request == null){
            request = new ListOrderItemsRequest()
            request.sellerId = this.config().current().sellerId()
        }
        return request
    }
}
