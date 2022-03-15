package xyz.cloudcoffee.atlas.api.common

interface Bookshelf<T> {
    val items : ArrayList<CloudObject<T>>
}