package top.guyi.amazon.mws.request.impl.report

import com.amazonaws.mws.model.GetReportListRequest
import com.amazonaws.mws.model.IdList
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ReportClient
import top.guyi.amazon.mws.conf.AmazonConfigFactory
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.report.GetReportListHandler

/**
 * Created by 古逸 on 2017-05-24.
 */
class GetReportListPackers extends SuperRequestPackers<GetReportListPackers,GetReportListRequest,GetReportListHandler>{

    private GetReportListRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return ReportClient.class
    }

    @Override
    String method() {
        return 'getReportList'
    }

    @Override
    GetReportListRequest amazonRequest() {
        if(request == null){
            request = new GetReportListRequest()
            request.merchant = this.config().current().sellerId()
        }
        return request
    }

    GetReportListPackers reportRequestIdList(List<String> idList){
        IdList list = new IdList()
        list.id = idList
        this.amazonRequest().reportRequestIdList = list
        return this
    }

    GetReportListPackers reportRequestId(String id){
        return this.reportRequestIdList([id])
    }

}
