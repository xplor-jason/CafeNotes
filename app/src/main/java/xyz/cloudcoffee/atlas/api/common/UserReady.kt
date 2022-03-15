package xyz.cloudcoffee.atlas.api.common

interface UserReady {
    fun onEnter(uid : String)
    fun onExit()
}