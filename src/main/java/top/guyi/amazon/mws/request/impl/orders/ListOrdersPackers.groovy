package top.guyi.amazon.mws.request.impl.orders

import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.OrdersClient
import top.guyi.amazon.mws.helper.DateHelper
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.orders.ListOrdersHandler

/**
 * Created by 古逸 on 2017-07-28.
 */
class ListOrdersPackers extends SuperRequestPackers<ListOrdersPackers,ListOrdersRequest,ListOrdersHandler>{

    private ListOrdersRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return OrdersClient.class
    }

    @Override
    String method() {
        return 'listOrders'
    }

    ListOrdersPackers after(Date date){
        this.amazonRequest().createdAfter = DateHelper.convertToXMLGregorianCalendar(date)
        return this
    }

    ListOrdersPackers before(Date date){
        this.amazonRequest().createdBefore = DateHelper.convertToXMLGregorianCalendar(date)
        return this
    }

    ListOrdersPackers status(List<String> status){
        this.amazonRequest().orderStatus = status
        return this
    }

    @Override
    ListOrdersRequest amazonRequest() {
        if(request == null){
            request = new ListOrdersRequest()
            request.sellerId = this.config().current().sellerId()
            request.marketplaceId = [this.config().current().marketplaceId()]
        }
        return request
    }
}
