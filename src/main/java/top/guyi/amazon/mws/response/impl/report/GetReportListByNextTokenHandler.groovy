package top.guyi.amazon.mws.response.impl.report

import com.amazonaws.mws.model.GetReportListByNextTokenRequest
import com.amazonaws.mws.model.GetReportListByNextTokenResponse
import com.amazonaws.mws.model.GetReportListRequest
import com.amazonaws.mws.model.GetReportListResponse
import top.guyi.amazon.mws.response.SuperResponseHandler
import top.guyi.amazon.mws.response.entity.report.Report

import javax.xml.datatype.DatatypeFactory

/**
 * Created by 古逸 on 2017-05-25.
 */
class GetReportListByNextTokenHandler extends SuperResponseHandler<GetReportListByNextTokenResponse, GetReportListByNextTokenRequest>{

    String nextToken

    boolean hasNext

    List<Report> reports = []

    @Override
    String getXML(GetReportListByNextTokenResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return [EntityData.create(
                /<ReportInfo><ReportId>(.*?)<\/ReportId><ReportType>(.*?)<\/ReportType><ReportRequestId>(.*?)<\/ReportRequestId><AvailableDate>(.*?)<\/AvailableDate><Acknowledged>(.*?)<\/Acknowledged><\/ReportInfo>/,{line->
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
