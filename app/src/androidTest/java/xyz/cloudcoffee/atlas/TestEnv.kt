package xyz.cloudcoffee.atlas

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.framework.AtlasApp
import xyz.cloudcoffee.atlas.util.Await

interface TestWork {
    fun doWork()
}

class TestEnv {
    companion object {
        val EMAIL = "jasonmilo2022@gmail.com"
        val PASSWORD = "laptop187"
        val UID = "A6kQftBUGkg0HObFrDLQ2Mz9gCD3"

        val caller : CloudCaller = AtlasApp.mainCaller

        fun out(msg : String){
            Log.v("testenv", msg)
        }
        fun testContext(): Context {
            return InstrumentationRegistry.getInstrumentation().context
        }
        fun testCloud(): Cloud {
            return AtlasApp.cloud()
        }

        suspend fun testRootLogin(){
            AtlasApp.cloud().auth.login(EMAIL, PASSWORD)
        }

        fun <T> awaitWork(name : String, work : (waiter : Await<T>) -> Unit): T {
            out("Running work $name")
            val waiter = Await<T>()
            GlobalScope.launch {
                withContext(Dispatchers.Default){
                    work(waiter)
                }
            }
            return waiter.waitfor()
        }
    }
}