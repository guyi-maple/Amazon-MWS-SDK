package top.guyi.amazon.mws.response

import top.guyi.amazon.mws.conf.AmazonConfigProvider
import top.guyi.amazon.mws.response.entity.ResponseMetaData

/**
 * Created by 古逸 on 2017-05-25.
 */
abstract class SuperResponseHandler<P,R> {

   class EntityData{
       String pattern
       Closure closure

       static EntityData create(String pattern, Closure closure) {
           EntityData ed = EntityData.class.newInstance()
           ed.pattern = pattern
           ed.closure = closure
           return ed
       }
   }

   ResponseMetaData meteData

   P response

   R request

   AmazonConfigProvider config

   P response(){
       return this.response
   }

   R request(){
       return this.request
   }

   AmazonConfigProvider config(){
       return this.config
   }

   abstract String getXML(P response)

   abstract List<EntityData> pattern();

   void over(){

   }

   SuperResponseHandler init(P response,R request,AmazonConfigProvider config){
        String xml = this.getXML(response)
        this.response = response
        this.request = request
        this.config = config
        def m = xml =~ /<ResponseMetadata><RequestId>(.*?)<\/RequestId><\/ResponseMetadata>/
        meteData = new ResponseMetaData()
        m.each {line->
            meteData.requestId = line[1]
        }
        if(this.pattern() != null && this.pattern().size() > 0){
            this.pattern().each {e->
                m = xml =~ e.pattern
                m.each {line->
                    e.closure(line)
                }
            }
        }
       this.over()
        return this
    }
}
