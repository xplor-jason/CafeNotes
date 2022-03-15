package xyz.cloudcoffee.atlas.model

class NoteModel(
    val title : String,
    val note : String
    )
{
    internal constructor() : this("", "") {

    }
}