package top.guyi.amazon.mws.request.impl.report

import com.amazonaws.mws.model.RequestReportRequest
import com.amazonaws.mws.model.RequestReportResponse
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ReportClient
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.report.RequestReportHandler

/**
 * Created by 古逸 on 2017-05-24.
 */
class RequestReportPackers extends SuperRequestPackers<RequestReportPackers,RequestReportRequest,RequestReportHandler>{

    private RequestReportRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass(){
        return ReportClient.class
    }

    @Override
    String method(){
        return 'requestReport'
    }

    @Override
    RequestReportRequest amazonRequest() {
        if(request == null){
            request = new RequestReportRequest()
            request.merchant = config().current().sellerId()
        }
        return request
    }

    RequestReportPackers must(String reportType) {
        this.amazonRequest().reportType = reportType
        return this
    }
}
