package xyz.cloudcoffee.atlas.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AtlasPrefs(context : Context) {
    companion object {
        private val KEY_PREFS_ERROR = "prefs.error"
    }

    private val sharedPrefs : SharedPreferences = context.getSharedPreferences("AtlasPrefs",
        Context.MODE_PRIVATE)

    fun consumeError(callback : (errorMessage : String) -> Unit){
        val error = sharedPrefs.getString(KEY_PREFS_ERROR, null)
        error?.let {
            callback(it)
            putError(null)
        }
    }
    fun putError(errorMessage : String?){
        sharedPrefs.edit {
            putString(KEY_PREFS_ERROR, errorMessage)
            commit()
        }
    }

}