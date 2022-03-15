package xyz.cloudcoffee.atlas.api.module

import xyz.cloudcoffee.atlas.api.common.Bookshelf
import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.api.common.CloudObject

interface CloudDatabase {
    suspend fun <T> publish(caller : CloudCaller, volume : String, data : T): CloudObject<T>
    suspend fun <T> volume(caller : CloudCaller, volume : String, convert : Class<T>): Bookshelf<T>
    suspend fun delistWithId(caller: CloudCaller, volume : String, pageId : String)
    suspend fun delist(caller : CloudCaller, page : String)

}