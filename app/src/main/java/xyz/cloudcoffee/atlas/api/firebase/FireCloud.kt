package xyz.cloudcoffee.atlas.api.firebase

import com.google.firebase.FirebaseApp
import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.CloudRepo
import xyz.cloudcoffee.atlas.api.module.CloudFactory

class FireCloud(app : FirebaseApp, override val factory : CloudFactory) : Cloud {
    override val helper =           FireHelper(factory)
    override val auth =             FireAuth(app)
    override val repo: CloudRepo =  FireRepo(this, factory)
    override val database =         FireDatabase(this, factory, app)
}