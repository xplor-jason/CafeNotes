package xyz.cloudcoffee.atlas.api.module

import xyz.cloudcoffee.atlas.api.common.Act
import xyz.cloudcoffee.atlas.api.common.UserReady

interface CloudAuth {

    fun userReady(listener : UserReady): Act
    fun currentUser(): String?

    suspend fun login(email : String, password : String): String
    suspend fun signout():Int
    suspend fun createAccount(email : String, password : String):String
    suspend fun sendVerifyEmail(email : String)

}