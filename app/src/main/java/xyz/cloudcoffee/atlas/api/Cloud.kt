package xyz.cloudcoffee.atlas.api

import xyz.cloudcoffee.atlas.api.module.CloudAuth
import xyz.cloudcoffee.atlas.api.module.CloudDatabase
import xyz.cloudcoffee.atlas.api.module.CloudHelper
import xyz.cloudcoffee.atlas.api.module.CloudFactory

interface Cloud {
    val auth :      CloudAuth
    val helper:     CloudHelper
    val database :  CloudDatabase
    val repo :      CloudRepo
    val factory :   CloudFactory
}