package top.guyi.amazon.mws.exception.config

/**
 * Created by 古逸 on 2017-05-24.
 */
class ConfigVersionExistException extends RuntimeException{

    ConfigVersionExistException(String version) {
        super('config version "' + version + '" exist')
    }
}
