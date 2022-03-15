package xyz.cloudcoffee.atlas.api.asyc

import kotlinx.coroutines.GlobalScope
import java.lang.Exception

class CloudJob<T>(block : ()-> T) {

    companion object {
        fun <T> create(block: () -> T): CloudJob<T>{
            return CloudJob<T>(block)
        }
    }

    var result : T? = null
    var exception : Exception? = null

    init {
        try {
            result = block()
        }catch (e : Exception){
            exception = e
        }
    }

    fun onSuccess(callback : (result : T) -> Unit){

    }
}