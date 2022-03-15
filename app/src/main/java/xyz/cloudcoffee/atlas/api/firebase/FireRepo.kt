package xyz.cloudcoffee.atlas.api.firebase

import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.CloudRepo
import xyz.cloudcoffee.atlas.api.firebase.repos.FireNotesRepo
import xyz.cloudcoffee.atlas.api.module.CloudFactory
import xyz.cloudcoffee.atlas.api.repo.NotesRepo

class FireRepo(cloud : Cloud, factory : CloudFactory) : CloudRepo {

    override val notes: NotesRepo = FireNotesRepo(cloud, factory)

}