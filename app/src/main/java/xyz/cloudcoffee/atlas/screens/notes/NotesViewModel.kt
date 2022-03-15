package xyz.cloudcoffee.atlas.screens.notes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.cloudcoffee.atlas.api.RequestContext
import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.api.repo.NotesRepo
import xyz.cloudcoffee.atlas.framework.Atlas
import xyz.cloudcoffee.atlas.framework.AtlasApp
import xyz.cloudcoffee.atlas.model.NoteLiveData
import xyz.cloudcoffee.atlas.model.NoteModel
import xyz.cloudcoffee.atlas.postSelf
import xyz.cloudcoffee.atlas.transaction

/**
 * To add new LiveData, make sure to add postSelf to 'postViewModel function'
 */
class NotesViewModel(lifeCycle : LifecycleOwner, val notesRepo: NotesRepo) : ViewModel(), LifecycleObserver, DefaultLifecycleObserver {
    private val VOLUME_NOTES = "notes"

    private var isUpdating : Boolean = false

    private val liveNotes : NoteLiveData = NoteLiveData(NoteModel("Def", "Def"))

    /*
     val counter = flow<Int> {
        var myCount = 0
        var isConnected = true

        while (isConnected){
            delay(1000)
            emit(myCount)
            myCount++
        }
    }
    val emitNotes = flow<NoteModel> {
    }*/


    //Live data declaretions
    private val notesList : MutableLiveData<ArrayList<CloudObject<NoteModel>>> = MutableLiveData(
        ArrayList())

    init {
        lifeCycle.lifecycle.addObserver(this)

        viewModelScope.launch {
            /*counter.collect {
                Atlas.out("My Count : $it")
            }*/
        }
    }
    fun postViewModel(){
        notesList.postSelf()
    }

    /* Call a lamda transaction on self & update LiveData &  */
    fun withThis(action : (viewModel : NotesViewModel) -> Unit){
        with(this){
            action(this)
        }
        postViewModel()
    }

    fun withNotesList(action : (list : ArrayList<CloudObject<NoteModel>>) -> Unit){
        notesList.value?.let {
            action(it)
        }
        notesList.postSelf()
    }

    fun getNotesList(): MutableLiveData<ArrayList<CloudObject<NoteModel>>> {
        isUpdating.let {
            if(!it) {
                isUpdating = true
                viewModelScope.launch {
                    val list = notesRepo.volumeNote(RequestContext(viewModelScope), VOLUME_NOTES)
                    notesList.value = list.items

                    isUpdating = false
                }
            }
        }
        return notesList
    }

    fun getLiveNoted(): NoteLiveData {
        return liveNotes
    }


    fun removeNote(note : CloudObject<NoteModel>, success: () -> Unit){
        viewModelScope.launch {
            notesRepo.delistNote(RequestContext(viewModelScope), note)
            notesList.transaction(success) {
                it.remove(note)
            }
        }
    }
    fun addNote(note : NoteModel, success: () -> Unit) {
        viewModelScope.launch {
            val ref = notesRepo.publishNote(RequestContext(viewModelScope), VOLUME_NOTES, note)
            notesList.transaction(success) {
                it.add(ref)
            }
        }
    }


    fun addNoteOLD(note : NoteModel, success : () -> Unit) {
        viewModelScope.launch {
            val ref = notesRepo.publishNote(RequestContext(viewModelScope), VOLUME_NOTES, note)
            withContext(Dispatchers.Main){
                notesList.transaction(success) {

                }
                //update()
            }
        }
    }

    fun addNoteAsyc(){

    }

    fun update(){
        getNotesList()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        update()
    }
}