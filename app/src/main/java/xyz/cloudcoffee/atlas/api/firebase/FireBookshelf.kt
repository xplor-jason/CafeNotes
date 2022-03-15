package xyz.cloudcoffee.atlas.api.firebase

import xyz.cloudcoffee.atlas.api.common.Bookshelf
import xyz.cloudcoffee.atlas.api.common.CloudObject

class FireBookshelf<T>(override val items: ArrayList<CloudObject<T>>) : Bookshelf<T> {

}