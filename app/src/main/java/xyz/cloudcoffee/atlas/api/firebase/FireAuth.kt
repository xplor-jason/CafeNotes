package xyz.cloudcoffee.atlas.api.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import xyz.cloudcoffee.atlas.api.common.Act
import xyz.cloudcoffee.atlas.api.common.UserReady
import xyz.cloudcoffee.atlas.api.module.CloudAuth
import java.lang.RuntimeException
import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FireAuth(val app : FirebaseApp) : CloudAuth {
    val userListeners = ArrayList<UserReady>()
    val auth : FirebaseAuth = FirebaseAuth.getInstance(app)

    init {
        auth.addAuthStateListener {
            val uid = it.uid
            if(uid != null)
                userListeners.forEach { it.onEnter(uid) }
            else
                userListeners.forEach { it.onExit() }
        }
    }

    companion object {
        fun extractAuthResult(cont : Continuation<String>, auth : AuthResult){
            val uid = auth.user?.uid
            if(uid != null)
                cont.resumeWith(Result.success(uid))
            else
                cont.resumeWith(Result.failure(RuntimeException("User UID does not exist")))
        }
    }

    override fun userReady(listener: UserReady):Act {
        userListeners.add(listener)
        return object : Act {
            override fun doAction() {
                userListeners.remove(listener)
            }
        }
    }

    override fun currentUser(): String? {
        return auth.currentUser?.uid
    }

    override suspend fun login(email: String, password: String): String {
        return suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { auth ->
                extractAuthResult(cont, auth)
            }.addOnFailureListener {
                cont.resumeWith(Result.failure(it))
            }
        }
    }

    override suspend fun signout():Int
    {
        auth.signOut()
        return 0
    }

    override suspend fun createAccount(email: String, password: String): String {
        return suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { auth->
                extractAuthResult(cont, auth)
            }.addOnFailureListener { e ->
                cont.resumeWith(Result.failure(e))
            }
        }
    }

    override suspend fun sendVerifyEmail(email: String) {
        return suspendCoroutine { cont ->
            auth.currentUser?.sendEmailVerification()
        }
    }
}