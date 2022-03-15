package xyz.cloudcoffee.atlas.screens.login

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import xyz.cloudcoffee.atlas.databinding.ActivityLoginBinding
import xyz.cloudcoffee.atlas.util.ScreenModel

class ScreenLogin : ScreenModel<ActivityLoginBinding> {
    lateinit var vbind : ActivityLoginBinding

    override fun onCreateModel(view: ActivityLoginBinding) {
        vbind = view
    }

    fun setTitle(title : String){
        return vbind.textTitle.run {
            text = title
        }
    }
    fun getTitle(): String {
        return vbind.textTitle.run {
            text.toString()
        }
    }

    fun getEmail(): String {
        return vbind.editEmail.text.toString()
    }
    fun getPassword(): String {
        return vbind.editPassword.text.toString()
    }

    fun setOnLogin(call : View.OnClickListener){
        vbind.btnLogin.setOnClickListener(call)
    }
}