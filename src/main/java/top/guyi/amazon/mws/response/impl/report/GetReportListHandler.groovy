package top.guyi.amazon.mws.response.impl.report

import com.amazonaws.mws.model.GetReportListRequest
import com.amazonaws.mws.model.GetReportListResponse
import top.guyi.amazon.mws.response.SuperResponseHandler
import top.guyi.amazon.mws.response.entity.report.Report

import javax.xml.datatype.DatatypeFactory
import java.text.SimpleDateFormat

/**
 * Created by 古逸 on 2017-05-25.
 */
class GetReportListHandler extends SuperResponseHandler<GetReportListResponse,GetReportListRequest>{

    String nextToken

    boolean hasNext

    List<Report> reports = []

    @Override
    String getXML(GetReportListResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return [EntityData.create(
                /<ReportInfoList><ReportId>(.*?)<\/ReportId><ReportType>(.*?)<\/ReportType><ReportRequestId>(.*?)<\/ReportRequestId><AvailableDate>(.*?)<\/AvailableDate><Acknowledged>(.*?)<\/Acknowledged><\/ReportInfoList>/,{line->
                    Report report = new Report()
                    report.reportId = line[1]
                    report.reportType = line[2]
                    report.reportRequestId = line[3]
                    report.availableDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(line[4]).toGregorianCalendar().getTime()
                    report.acKnowLedGed = Boolean.valueOf(line[5])
                    reports.add(report)
                }
        ),EntityData.create(/<NextToken>(.*?)<\/NextToken><HasNext>(.*?)<\/HasNext>/,{line->
            this.nextToken = line[1]
            this.hasNext = Boolean.valueOf(line[2])
        })]
    }


    @Override
    public String toString() {
        return "GetReportListHandler{" +
                "response=" + response +
                ", nextToken='" + nextToken + '\'' +
                ", hasNext=" + hasNext +
                ", reports=" + reports +
                '}';
    }
}
