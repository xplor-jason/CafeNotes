package xyz.cloudcoffee.atlas.framework

import android.app.Application
import com.google.firebase.FirebaseApp
import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.api.firebase.FireCloud
import xyz.cloudcoffee.atlas.api.firebase.FireFactory
import xyz.cloudcoffee.atlas.sharedprefs.AtlasPrefs
import java.util.*

class AtlasApp : Application() {
    val runtimeId = "atlas-runtime= ${UUID.randomUUID()}"

    companion object {
        lateinit var mainCaller : CloudCaller

        private lateinit var myCloud : Cloud

        fun cloud():Cloud {
            return myCloud
        }
        fun main():CloudCaller {
            return mainCaller
        }
    }

    override fun onCreate() {
        super.onCreate()
        val app = FirebaseApp.initializeApp(this)

        app?.let {
            myCloud = FireCloud(it, FireFactory())
            mainCaller = myCloud.factory.createCaller("main")
        }


    }
}