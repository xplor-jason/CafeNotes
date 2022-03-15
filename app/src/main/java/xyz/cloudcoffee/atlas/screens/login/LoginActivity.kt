package xyz.cloudcoffee.atlas.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.cloudcoffee.atlas.api.common.UserReady
import xyz.cloudcoffee.atlas.databinding.ActivityLoginBinding
import xyz.cloudcoffee.atlas.framework.Atlas
import xyz.cloudcoffee.atlas.framework.AtlasActivity
import xyz.cloudcoffee.atlas.framework.AtlasApp
import xyz.cloudcoffee.atlas.screens.notes.NotesActivity
import java.lang.Exception

class LoginActivity : AtlasActivity<ActivityLoginBinding, ScreenLogin>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(AtlasApp.cloud().auth.currentUser() != null)
            startNotesActivity()
    }

    override fun onCreateView(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(inflater)
    }

    override fun onBindActivity(view: ActivityLoginBinding) {
        screen().setOnLogin {
            val email = screen().getEmail()
            val pass = screen().getPassword()
            Atlas.out("Logging in with $email = $pass")
            GlobalScope.launch {
                try {
                    AtlasApp.cloud().auth.login(email, pass)
                    startNotesActivity()
                }
                catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginActivity, "Error Logging in: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onScreenClass(): Class<ScreenLogin> {
        return ScreenLogin::class.java
    }

    fun startNotesActivity(){
        with(Intent()){
            setClass(this@LoginActivity, NotesActivity::class.java)
            startActivity(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Atlas.out("OnDestory Login")
    }

    override fun onStart() {
        super.onStart()
        Atlas.out("Login.start()")
    }
}