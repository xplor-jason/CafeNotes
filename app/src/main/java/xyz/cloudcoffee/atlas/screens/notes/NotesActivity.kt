package xyz.cloudcoffee.atlas.screens.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.cloudcoffee.atlas.databinding.ActivityNotesBinding
import xyz.cloudcoffee.atlas.dialog.ErrorDialog
import xyz.cloudcoffee.atlas.framework.Atlas
import xyz.cloudcoffee.atlas.framework.AtlasActivity
import xyz.cloudcoffee.atlas.framework.AtlasApp
import xyz.cloudcoffee.atlas.screens.login.LoginActivity
import java.lang.RuntimeException
import java.util.*

class NotesActivity : AtlasActivity<ActivityNotesBinding, ScreenNotes>() {
    lateinit var viewModel : NotesViewModel

    override fun onScreenClass(): Class<ScreenNotes> {
        return ScreenNotes::class.java
    }
    override fun onCreateView(inflater: LayoutInflater): ActivityNotesBinding {
        return ActivityNotesBinding.inflate(inflater)
    }

    override fun onBindActivity(view: ActivityNotesBinding) {
        //viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        viewModel = ViewModelProvider(this, NotesViewModelFactory(this)).get(NotesViewModel::class.java)



        screen().setSignoutClickListener() {
            GlobalScope.launch {
                AtlasApp.cloud().auth.signout()
                val intent = with(Intent()){
                    setClass(this@NotesActivity, LoginActivity::class.java)
                }
                startActivity(intent)
            }
        }

        screen().setAddNoteClickListener {
            val rand = Random().nextDouble()
            Atlas.out("Random : $rand")


            val newNote = screen().getEditNote()
            viewModel.addNote(newNote){
                with(screen()){
                    resetEditNotes()
                    addCount()
                }
            }
        }

        screen().setCrashMeClickListener {
            throw RuntimeException("This is a app crash FAIL")
        }

        viewModel.getNotesList().observe(this) { arrayNotes ->
            screen().setNoteAdapter(NotesAdapter(viewModel).apply {
                setList(arrayNotes)
            })
        }

        viewModel.getLiveNoted().observe(this){
            Atlas.out("Live Note: ${it.title}")
        }

        viewModel.viewModelScope.launch {
            while(true){
                delay(5000)
                viewModel.getLiveNoted().next()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Atlas.out("Notes.onStart()")
    }

}

