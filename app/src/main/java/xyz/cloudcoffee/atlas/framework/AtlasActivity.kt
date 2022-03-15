package xyz.cloudcoffee.atlas.framework

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import xyz.cloudcoffee.atlas.api.common.Act
import xyz.cloudcoffee.atlas.api.common.UserReady
import xyz.cloudcoffee.atlas.dialog.ErrorDialog
import xyz.cloudcoffee.atlas.screens.login.LoginActivity
import xyz.cloudcoffee.atlas.sharedprefs.AtlasPrefs
import xyz.cloudcoffee.atlas.util.ScreenModel

open abstract class AtlasActivity<T : androidx.viewbinding.ViewBinding, M : ScreenModel<T>> : AppCompatActivity(){
    var activityView:   T? = null
    var activityScreen: M? = null

    abstract fun onCreateView(inflater: LayoutInflater): T
    abstract fun onBindActivity(view : T)

    abstract fun onScreenClass():Class<M>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityView = onCreateView(layoutInflater)
        activityScreen = onScreenClass().newInstance()
        activityScreen!!.onCreateModel(activityView!!)
        onBindActivity(activityView!!)
        setContentView(activityView!!.root)

        //Handle app error from any previous crash
        val prefs = AtlasPrefs(this)
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            prefs.putError(throwable.message)
            System.exit(1)
        }



    }

    override fun onResume() {
        super.onResume()
        val prefs = AtlasPrefs(this)
        prefs.consumeError {
            Atlas.out("ERROR: $it")
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            //val errorDialog = ErrorDialog(it)
            //errorDialog.show(supportFragmentManager,"error-dialog")
        }
    }

    fun view(): T {
        return activityView!!
    }
    fun screen(): M {
        return activityScreen!!
    }
}