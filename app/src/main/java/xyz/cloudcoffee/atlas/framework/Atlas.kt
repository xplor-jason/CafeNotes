package xyz.cloudcoffee.atlas.framework

import android.util.Log

class Atlas
{
    companion object {
        val EMPTY : () -> Unit = fun(){}

        fun out(msg : String){
            Log.v("applog", msg)
        }
        fun check(expression : Boolean, caller : Any,  msg : String?){
            msg?.let {
                if(!expression){
                    val className = caller.javaClass.simpleName
                    Log.d("appdebug", "AssertFail= $className -> $it")
                }
            }
        }
    }
}