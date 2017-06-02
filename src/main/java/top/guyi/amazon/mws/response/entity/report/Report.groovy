package top.guyi.amazon.mws.response.entity.report

/**
 * Created by 古逸 on 2017-05-25.
 */
class Report {

    String reportId

    String reportType

    String reportRequestId

    Date startDate

    Date endDate

    boolean scheduled

    Date submittedDate

    Date availableDate

    boolean acKnowLedGed

    String reportProcessingStatus

    @Override
    public String toString() {
        return "Report{" +
                "reportId='" + reportId + '\'' +
                ", reportType='" + reportType + '\'' +
                ", reportRequestId='" + reportRequestId + '\'' +
                ", availableDate=" + availableDate +
                ", acKnowLedGed=" + acKnowLedGed +
                '}';
    }
}
