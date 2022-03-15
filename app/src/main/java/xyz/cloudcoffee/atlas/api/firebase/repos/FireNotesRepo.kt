package xyz.cloudcoffee.atlas.api.firebase.repos

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import xyz.cloudcoffee.atlas.api.Cloud
import xyz.cloudcoffee.atlas.api.RequestContext
import xyz.cloudcoffee.atlas.api.asyc.CloudJob
import xyz.cloudcoffee.atlas.api.common.Bookshelf
import xyz.cloudcoffee.atlas.api.common.CloudCaller
import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.api.module.CloudFactory
import xyz.cloudcoffee.atlas.api.repo.NotesRepo
import xyz.cloudcoffee.atlas.model.NoteModel
import java.lang.Exception

class FireNotesRepo(val cloud : Cloud, factory : CloudFactory) : NotesRepo {
    val noteCaller : CloudCaller = factory.createCaller("repo@notes")

    override suspend fun publishNote(requestContext: RequestContext, volume : String, note: NoteModel):CloudObject<NoteModel> {
        return cloud.database.publish(noteCaller,volume,note)

    }

    override suspend fun delistNote(requestContext: RequestContext, note: CloudObject<NoteModel>) {
       cloud.database.delist(noteCaller, note.page)
    }

    override suspend fun delistNoteWithId(requestContext: RequestContext, volume: String, noteId: String) {
        cloud.database.delist(noteCaller, "$volume/$noteId")
    }

    override suspend fun volumeNote(requestContext: RequestContext, volume: String): Bookshelf<NoteModel> {
        //return cloud.database.volume(noteCaller, volume, NoteModel::class.java)
        return cloud.database.volume(noteCaller, volume, NoteModel::class.java)
    }
}