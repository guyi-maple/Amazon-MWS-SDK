package top.guyi.amazon.mws.response.impl.report

import com.amazonaws.mws.model.GetReportRequest
import com.amazonaws.mws.model.GetReportResponse
import top.guyi.amazon.mws.conf.AmazonConfigProvider
import top.guyi.amazon.mws.response.SuperResponseHandler

/**
 * Created by 古逸 on 2017-05-25.
 */
class GetReportHandler extends SuperResponseHandler<GetReportResponse,GetReportRequest>{

    List<List<String>> textRaws(){
        def outputStream = this.request().reportOutputStream
        if(outputStream instanceof ByteArrayOutputStream){
            outputStream = (ByteArrayOutputStream)outputStream
            return this.textRaws(new String(outputStream.toByteArray(),this.config().charset()))
        }else{
            return null
        }
    }

    List<List<String>> textRaws(File file){
        Scanner sc = new Scanner(file)
        def data = []
        while(sc.hasNextLine()){
            data.add(sc.nextLine().split('\t'))
        }
        sc.close()
        return data
    }

    List<List<String>> textRaws(String str){
        Scanner sc = new Scanner(str)
        def data = []
        while(sc.hasNextLine()){
            data.add(sc.nextLine().split('\t'))
        }
        sc.close()
        return data
    }

    @Override
    String getXML(GetReportResponse response) {
        return response.toXML()
    }

    @Override
    List<EntityData> pattern() {
        return null
    }
}
