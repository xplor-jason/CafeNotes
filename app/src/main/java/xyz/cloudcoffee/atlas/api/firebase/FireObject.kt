package xyz.cloudcoffee.atlas.api.firebase

import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.api.module.ObjectParams

class FireObject<T>(params : ObjectParams<T>): CloudObject<T> {
    private val data : T = params.data

    override val page: String = params.page
    override fun value(callback: (data: T) -> Unit) {
        callback(data)
    }

    override fun toString(): String {
        return page
    }

    override fun equals(other: Any?): Boolean {
        if (other is CloudObject<*>) {
            val castOther = other as CloudObject<T>
            return this.page.equals(castOther.page)
        }
        return false;
    }
}