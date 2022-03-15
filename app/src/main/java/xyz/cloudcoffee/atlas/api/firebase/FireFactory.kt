package xyz.cloudcoffee.atlas.api.firebase

import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.api.module.CloudFactory
import xyz.cloudcoffee.atlas.api.module.ObjectParams

class FireFactory : CloudFactory {
    override fun <T> createCloudObject(params: ObjectParams<T>): CloudObject<T> {
        return FireObject(params)
    }

    override fun createCaller(name: String): CloudCaller {
        return FireCaller(name)
    }
}