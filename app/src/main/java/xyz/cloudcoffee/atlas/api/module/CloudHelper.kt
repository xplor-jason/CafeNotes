package xyz.cloudcoffee.atlas.api.module

interface CloudHelper {
    fun genUUID(): String
    fun randomInt(bound : Int): Int
    fun randomInt(min : Int, max : Int): Int


    fun randomItem(list: Array<String>): Any
}