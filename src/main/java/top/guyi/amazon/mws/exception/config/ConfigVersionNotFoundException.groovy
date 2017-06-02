package top.guyi.amazon.mws.exception.config

/**
 * Created by 古逸 on 2017-05-24.
 */
class ConfigVersionNotFoundException extends RuntimeException{

    ConfigVersionNotFoundException(String version) {
        super('not found config version by "' + version + '"')
    }
}
