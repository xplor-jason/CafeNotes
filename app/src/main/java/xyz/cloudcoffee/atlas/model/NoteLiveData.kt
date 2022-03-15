package xyz.cloudcoffee.atlas.model

import androidx.lifecycle.LiveData

class NoteLiveData(val data : NoteModel) : LiveData<NoteModel>(){
    private var counter = 0
    init {
        value = data
    }

    fun next(){
        value = NoteModel("Next: $counter++", "Next counter $counter")
        counter++
    }
}