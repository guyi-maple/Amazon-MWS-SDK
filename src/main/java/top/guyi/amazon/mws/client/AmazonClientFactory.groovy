package top.guyi.amazon.mws.client

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import top.guyi.amazon.mws.conf.AmazonConfigFactory


/**
 * Created by 古逸 on 2017-05-24.
 */

class AmazonClientFactory implements InitializingBean{

    @Autowired
    List<SuperAmazonClient> clients

    private static amazonClients = [:]

    static AmazonClientFactory factory
    @Override
    void afterPropertiesSet() throws Exception {
        this.clients.each {
            amazonClients[it.class] = [:]
            amazonClients[it.class]['Object'] = it
        }
        AmazonClientFactory.factory = this
    }

    static ThreadLocal<Closure> configcus = new ThreadLocal<Closure>()
    def get(Class<? extends SuperAmazonClient> clazz){
        if(configcus.get() == null){
            if(amazonClients[clazz][AmazonConfigFactory.factory.currentVersion()] == null){
                amazonClients[clazz][AmazonConfigFactory.factory.currentVersion()] = amazonClients[clazz]['Object'].create(AmazonConfigFactory.factory.current())
            }
            return amazonClients[clazz][AmazonConfigFactory.factory.currentVersion()]
        }else{
            def conf = amazonClients[clazz]['Object'].newConfig(AmazonConfigFactory.factory.current())
            configcus.get()(conf)
            return amazonClients[clazz]['Object'].createByConfig(AmazonConfigFactory.factory.current(),conf)
        }
    }
}
