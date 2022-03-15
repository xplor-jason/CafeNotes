package xyz.cloudcoffee.atlas.api.module

import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.api.common.CloudObject

class ObjectParams<T>(
    val cloud : Cloud,
    val page : String,
    val data : T)
{
}

interface CloudFactory {
    fun <T> createCloudObject(params : ObjectParams<T>): CloudObject<T>
    fun createCaller(name : String): CloudCaller
}