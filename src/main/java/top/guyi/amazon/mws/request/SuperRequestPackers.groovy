package top.guyi.amazon.mws.request

import groovy.transform.Undefined.CLASS
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import top.guyi.amazon.mws.client.AmazonClientFactory
import top.guyi.amazon.mws.client.SuperAmazonClient
import top.guyi.amazon.mws.conf.AmazonConfigFactory
import top.guyi.amazon.mws.conf.AmazonConfigProvider
import top.guyi.amazon.mws.response.SuperResponseHandler

import javax.annotation.Resource

/**
 * Created by 古逸 on 2017-05-24.
 */
abstract class SuperRequestPackers<T,R,P> {

    private static Map<Class<SuperRequestPackers>,Class<P>> respClasses = new HashMap<Class<SuperRequestPackers>,Class<P>>()

    abstract Class<? extends SuperAmazonClient> clientClass()

    abstract String method()

    abstract R amazonRequest()

    T version(String version = 'default'){
        this.config().currentVersion(version)
        return this
    }

    AmazonConfigFactory config(){
        return AmazonConfigFactory.factory
    }

    T customize(Closure<R> closure){
        R request = this.amazonRequest()
        closure(request)
        return this
    }

    T customizeAmazonConfig(Closure<?> closure){
        AmazonClientFactory.configcus.set(closure)
        return this
    }

    def checkNull(name){
        return this.amazonRequest().getAt(name)
    }

    P invok(SuperResponseHandler response){
        if(response == null){
            response = this.getRespClass().newInstance()
        }
        def client = AmazonClientFactory.factory.get(this.clientClass())
        return response.init(client.invokeMethod(this.method(),this.amazonRequest()),this.amazonRequest(),this.config().current())
    }

    P invok(){
        return this.invok(null)
    }

    private Class<P> getRespClass(){
        if(respClasses.containsKey(this.getClass())){
            return respClasses.get(this.class)
        }else{
            def types = ((ParameterizedTypeImpl)this.class.getGenericSuperclass()).actualTypeArguments
            if(types.size() >= 3){
                Class<P> respClass = types[2]
                respClasses.put(this.class,respClass)
                return respClass
            }
        }
    }

}
