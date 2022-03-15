package xyz.cloudcoffee.atlas.screens.notes

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.cloudcoffee.atlas.framework.AtlasApp

class NotesViewModelFactory(val lifecycleOwner: LifecycleOwner) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(lifecycleOwner, AtlasApp.cloud().repo.notes) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}