package top.guyi.amazon.mws.conf

import org.springframework.beans.factory.InitializingBean
import top.guyi.amazon.mws.exception.config.ConfigFieldNullPointerException
import top.guyi.amazon.mws.exception.config.ConfigVersionExistException
import top.guyi.amazon.mws.exception.config.ConfigVersionNotFoundException

/**
 * 亚马逊配置工厂
 * Created by 古逸 on 2017-05-24.
 */
class AmazonConfigFactory implements InitializingBean{

    List<AmazonConfigProvider> providers

    private static Map<String,AmazonConfigProvider> configs = new HashMap<String,AmazonConfigProvider>()
    private static ThreadLocal<String> currentVersion = new ThreadLocal<String>()
    private static String globalVersion

    /**
     * 获取指定版本的配置文件
     * @param version 配置文件版本
     * @return
     */
    AmazonConfigProvider get(String version){
        if(!AmazonConfigFactory.configs.containsKey(version)){
            throw new ConfigVersionNotFoundException(version)
        }else{
            return AmazonConfigFactory.configs.get(version)
        }
    }

    /**
     * 添加配置文件提供者
     * @param provider 提供者
     * @param forced 是否强制覆盖，默认为false
     * @return
     */
    boolean addProvider(AmazonConfigProvider provider,boolean forced = false){
        if(AmazonConfigFactory.configs.containsKey(provider.version())){
            if(forced){
                AmazonConfigFactory.configs.put(provider.version(),provider)
            }else{
                throw new ConfigVersionExistException(provider.version())
            }
        }else{
            AmazonConfigFactory.configs.put(provider.version(),provider)
        }
    }

    /**
     * 设置当前线程配置文件版本
     * @param version 配置文件
     * @return
     */
    def currentVersion(String version){
        if(!AmazonConfigFactory.configs.containsKey(version)){
            throw new ConfigVersionNotFoundException(version)
        }else{
            AmazonConfigFactory.currentVersion.set(version)
        }
    }
    /**
     * 返回当前线程配置文件版本
     * @return
     */
    def currentVersion(){
        return AmazonConfigFactory.currentVersion.get()
    }
    /**
     * 得到当前线程的配置文件
     * @return
     */
    AmazonConfigProvider current(){
        if(this.currentVersion() == null){
            if(this.globalVersion() == null){
                return this.get('default')
            }else{
                return this.get(this.globalVersion())
            }
        }else{
            return this.get(this.currentVersion())
        }
    }

    /**
     * 设置全局配置文件版本
     * @param version 配置文件版本
     * @return
     */
    def globalVersion(String version){
        if(!AmazonConfigFactory.configs.containsKey(version)){
            throw new ConfigVersionNotFoundException(version)
        }else{
            AmazonConfigFactory.globalVersion = version
        }
    }
    /**
     * 获取全局配置文件版本
     * @return
     */
    def globalVersion(){
        return AmazonConfigFactory.globalVersion
    }
    /**
     * 得到当全局配置文件
     * @return
     */
    AmazonConfigProvider global(){
        if(this.globalVersion() == null){
            return this.get('default')
        }else{
            return this.get(this.globalVersion())
        }
    }

    static AmazonConfigFactory factory;
    @Override
    void afterPropertiesSet() throws Exception {
        this.generateConfigs()
        AmazonConfigFactory.factory = this
    }
    /**
     * 解析生成配置文件集合
     * @return
     */
    private generateConfigs(){
        this.providers.each {provider->
            if(provider.version() != null){
                if(provider.accessKey() != null){
                    if(provider.secretKey() != null){
                        if(provider.sellerId() != null){
                            if(provider.marketplaceId() != null){
                                configs.put(provider.version(),provider)
                            }else{
                                throw new ConfigFieldNullPointerException('marketplaceId',provider.class.simpleName)
                            }
                        }else{
                            throw new ConfigFieldNullPointerException('sellerId',provider.class.simpleName)
                        }
                    }else{
                        throw new ConfigFieldNullPointerException('secretKey',provider.class.simpleName)
                    }
                }else {
                    throw new ConfigFieldNullPointerException('accessKey',provider.class.simpleName)
                }
            }else {
                throw new ConfigFieldNullPointerException('version',provider.class.simpleName)
            }
        }
        providers = null
    }

}
