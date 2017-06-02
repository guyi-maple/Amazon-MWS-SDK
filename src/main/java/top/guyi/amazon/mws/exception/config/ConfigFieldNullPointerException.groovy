package top.guyi.amazon.mws.exception.config

/**
 * Created by 古逸 on 2017-05-24.
 */
class ConfigFieldNullPointerException extends RuntimeException{

    ConfigFieldNullPointerException(String field, String name) {
        super('config field "'+field+'" must not be null , in "' + name + '"')
    }
}
