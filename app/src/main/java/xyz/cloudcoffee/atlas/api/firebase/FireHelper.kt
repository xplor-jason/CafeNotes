package xyz.cloudcoffee.atlas.api.firebase

import xyz.cloudcoffee.atlas.api.module.CloudHelper
import xyz.cloudcoffee.atlas.api.module.CloudFactory
import java.util.*

class FireHelper(val factory : CloudFactory) : CloudHelper {

    companion object {
        private val CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKMLNOPQRSTUZ".toCharArray()
        private val RANDOM = Random()
    }


    override fun genUUID(): String {
        return "${UUID.randomUUID()}"
    }

    override fun randomInt(bound : Int): Int {
        return RANDOM.nextInt(bound)
    }

    override fun randomInt(min: Int, max: Int): Int {
        return RANDOM.nextInt(max + 1 - min) + min;
    }

    override fun randomItem(list: Array<String>): Any {
        return list[this.randomInt(list.size)]
    }
}