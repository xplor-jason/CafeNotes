package xyz.cloudcoffee.atlas.api.repo

import xyz.cloudcoffee.atlas.api.RequestContext
import xyz.cloudcoffee.atlas.api.asyc.CloudJob
import xyz.cloudcoffee.atlas.api.common.Bookshelf
import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.model.NoteModel

interface NotesRepo {
    suspend fun publishNote(requestContext: RequestContext, volume : String, note : NoteModel):CloudObject<NoteModel>
    suspend fun delistNote(requestContext: RequestContext, note : CloudObject<NoteModel>)
    suspend fun delistNoteWithId(requestContext: RequestContext, volume: String, noteId : String)
    suspend fun volumeNote(requestContext: RequestContext, volume : String): Bookshelf<NoteModel>
}