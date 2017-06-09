package top.guyi.amazon.mws.request.impl.report

import com.amazonaws.mws.model.GetReportListResponse
import com.amazonaws.mws.model.GetReportRequest
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.client.impl.ReportClient
import top.guyi.amazon.mws.conf.AmazonConfigFactory
import top.guyi.amazon.mws.request.SuperRequestPackers
import top.guyi.amazon.mws.response.impl.report.GetReportHandler

/**
 * Created by 古逸 on 2017-05-24.
 */
class GetReportPackers extends SuperRequestPackers<GetReportPackers,GetReportRequest,GetReportHandler>{

    private GetReportRequest request

    @Override
    Class<? extends SuperAmazonClient> clientClass() {
        return ReportClient.class
    }

    @Override
    String method() {
        return 'getReport'
    }

    @Override
    GetReportRequest amazonRequest() {
        if(request == null){
            request = new GetReportRequest()
            request.merchant = this.config().current().sellerId()
        }
        return request
    }

    GetReportPackers must(String reportId,OutputStream outputStream){
        this.amazonRequest().reportId = reportId
        this.amazonRequest().reportOutputStream = outputStream
        return this
    }

}
