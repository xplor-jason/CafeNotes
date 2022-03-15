package xyz.cloudcoffee.atlas.screens.notes

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.cloudcoffee.atlas.databinding.ActivityNotesBinding
import xyz.cloudcoffee.atlas.model.NoteModel
import xyz.cloudcoffee.atlas.util.ScreenModel

class ScreenNotes : ScreenModel<ActivityNotesBinding> {
    private var addCounter : Int = 0
    private lateinit var vbind : ActivityNotesBinding

    override fun onCreateModel(view: ActivityNotesBinding) {
        vbind = view
        vbind.recycler.layoutManager =
            LinearLayoutManager(view.root.context, LinearLayoutManager.VERTICAL, false)
    }

    fun setAddNoteClickListener(listener : View.OnClickListener){
        vbind.btnAddNote.setOnClickListener(listener)
    }
    fun setSignoutClickListener(listener: View.OnClickListener){
        vbind.btnSignout.setOnClickListener(listener)
    }
    fun setCrashMeClickListener(listener : View.OnClickListener){
        vbind.btnCrashMe.setOnClickListener(listener)
    }


    fun setNoteAdapter(adapter : NotesAdapter){
        vbind.recycler.adapter = adapter
    }
    fun getEditNote():NoteModel {
        return NoteModel(vbind.editTitle.text.toString(), vbind.editNote.text.toString())
    }
    fun resetEditNotes(){
        vbind.editTitle.setText("")
        vbind.editNote.setText("")
    }
    fun addCount(){
        addCounter++
        vbind.textCounter.setText("$addCounter")
    }
}