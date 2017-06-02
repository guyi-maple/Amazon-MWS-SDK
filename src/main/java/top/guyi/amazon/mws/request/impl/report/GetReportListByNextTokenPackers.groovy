package top.guyi.amazon.mws.request.impl.report

import com.amazonaws.mws.model.GetReportListByNextTokenRequest
import com.amazonaws.mws.model.GetReportListRequest
import com.amazonaws.mws.model.IdList
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ReportClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.report.GetReportListByNextTokenHandler
import top.guyi.amazon.mws.response.impl.report.GetReportListHandler

/**
 * Created by 古逸 on 2017-05-24.
 */
class GetReportListByNextTokenPackers extends SuperRequestPackers<GetReportListByNextTokenPackers,GetReportListByNextTokenRequest,GetReportListByNextTokenHandler>{

    private GetReportListByNextTokenRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return ReportClient.class
    }

    @Override
    String method() {
        return 'getReportListByNextToken'
    }

    @Override
    GetReportListByNextTokenRequest amazonRequest() {
        if(request == null){
            request = new GetReportListByNextTokenRequest()
            request.merchant = this.config().current().sellerId()
        }
        return request
    }

    GetReportListByNextTokenPackers must(String nextToken){
        this.amazonRequest().nextToken = nextToken
        return this
    }

}
