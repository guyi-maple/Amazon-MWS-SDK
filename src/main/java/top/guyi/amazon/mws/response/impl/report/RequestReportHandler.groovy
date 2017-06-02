package top.guyi.amazon.mws.response.impl.report

import com.amazonaws.mws.model.GetReportRequest
import com.amazonaws.mws.model.GetReportResponse
import com.amazonaws.mws.model.RequestReportRequest
import com.amazonaws.mws.model.RequestReportResponse
import top.guyi.amazon.mws.response.SuperResponseHandler
import top.guyi.amazon.mws.response.entity.report.Report

import javax.xml.datatype.DatatypeFactory

/**
 * Created by 古逸 on 2017-05-25.
 */
class RequestReportHandler extends SuperResponseHandler<RequestReportResponse,RequestReportRequest>{

    Report report

    @Override
    String getXML(RequestReportResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return [EntityData.create(
                /<ReportRequestInfo><ReportRequestId>(.*?)<\/ReportRequestId><ReportType>(.*?)<\/ReportType><StartDate>(.*?)<\/StartDate><EndDate>(.*?)<\/EndDate><Scheduled>(.*?)<\/Scheduled><SubmittedDate>(.*?)<\/SubmittedDate><ReportProcessingStatus>(.*?)<\/ReportProcessingStatus><\/ReportRequestInfo>/,{line->
                def d = DatatypeFactory.newInstance()
                report = new Report()
                report.reportRequestId = line[1]
                report.reportType = line[2]
                report.startDate = d.newXMLGregorianCalendar(line[3]).toGregorianCalendar().getTime()
                report.endDate = d.newXMLGregorianCalendar(line[4]).toGregorianCalendar().getTime()
                report.scheduled = Boolean.valueOf(line[5])
                report.submittedDate = d.newXMLGregorianCalendar(line[6]).toGregorianCalendar().getTime()
                report.reportProcessingStatus = line[7]
            }
        )]
    }
}
