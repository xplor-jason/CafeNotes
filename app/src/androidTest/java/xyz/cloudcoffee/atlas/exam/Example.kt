package xyz.cloudcoffee.atlas.exam

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import xyz.cloudcoffee.atlas.TestEnv
import xyz.cloudcoffee.atlas.api.common.Bookshelf
import xyz.cloudcoffee.atlas.api.common.UserReady
import xyz.cloudcoffee.atlas.framework.Atlas
import xyz.cloudcoffee.atlas.framework.AtlasApp
import xyz.cloudcoffee.atlas.model.NoteModel
import xyz.cloudcoffee.atlas.screens.login.ScreenLogin
import xyz.cloudcoffee.atlas.util.Await
import java.util.*

@RunWith(AndroidJUnit4::class)
class Example {
    @Test
    fun addRandomNotes(){
        val res = Await<Int>()


        Atlas.out("Main data on ${Thread.currentThread().name}")
        GlobalScope.launch {

            withContext(Dispatchers.IO){
                val uuid = "${UUID.randomUUID()}"
                Atlas.out("Posting: $uuid")
                val aNote = NoteModel("title= $uuid", "note= $uuid")
                val req = AtlasApp.cloud().database.publish(AtlasApp.main(), "notes", aNote)
                Atlas.out("Refer=${req.page}")
            }
            res.post(0)
        }
        //res.waitfor()

        Await.pause(5000)





    }
    @Test
    fun testPrintNotes(){
        val notes = TestEnv.awaitWork<Bookshelf<NoteModel>>("list notes"){
            GlobalScope.launch {
                val bookshelfNotes = AtlasApp.cloud().database.volume<NoteModel>(TestEnv.caller,"notes", NoteModel::class.java)
                it.post(bookshelfNotes)
            }
        }
        TestEnv.out("NoteListSize: ${notes.items.size}")
        notes.items.forEach {
            //TestEnv.out("MyNote: ${it.value().title}")
        }
    }

    @Test
    fun testAwaiter(){
        val make = TestEnv.awaitWork<String>("printNotes"){
            Thread.sleep(2000)
            it.post("Hello there")
        }
        TestEnv.out("Result of test $make")
    }

    @Test
    fun testWaiter(){
        TestEnv.awaitWork<Int>("Await Thread Sleep") {

            val run = object : Runnable {
                override fun run() {
                    Thread.sleep(5000)
                    it.post(0)
                }
            }
            val t1 = Thread(run)
            t1.start()

            //it.post(true)
        }
    }


    @Test
    fun testme(){
        val cloud = AtlasApp.cloud()
        TestEnv.out("Welcome to testme")

        val popcorn = AtlasApp.cloud().helper.genUUID()

        TestEnv.out(popcorn)

        cloud.auth.userReady(object : UserReady {
            override fun onEnter(uid: String) {
                TestEnv.out("User has logged in as $uid")
            }
            override fun onExit() {
                TestEnv.out("User is now signing out")
            }
        })

        val req = Await<String>()

        GlobalScope.launch {
            val uid = cloud.auth.login(TestEnv.EMAIL, TestEnv.PASSWORD)
            req.post(uid)
        }

        val uid = req.waitfor()
        Assert.assertEquals(uid, TestEnv.UID)
        TestEnv.out(uid)


        val listing = arrayOf("Melbourne", "Phone", "Computer")

        val item = cloud.helper.randomItem(listing)
        TestEnv.out("$item")

        val didWait = Await<Int>()

        GlobalScope.launch {
            cloud.auth.signout()
            didWait.post(0)
        }
        didWait.waitfor()

        val next = Await<Int>()

        GlobalScope.launch {
            TestEnv.testRootLogin()
            next.post(0)
        }
        next.waitfor()

        Await.pause(100)

        val aClass = ScreenLogin::class.java

        
    }

    //@Test
    fun createAccount(){

        val cloud = AtlasApp.cloud()

        val email = "jasonmilo2022+${cloud.helper.genUUID()}@gmail.com"
        val password = "laptop787"

        val req = Await<String>()

        GlobalScope.launch {
            val uid = cloud.auth.createAccount(email, password)
            req.post(uid)
        }

        val res = req.waitfor()
        TestEnv.out("created account for $res")
    }

    @Test
    fun fireAddNotes(){
        val res = Await<Int>()
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val bottle = NoteModel("a Note", "working notes")
                FirebaseFirestore.getInstance().collection("notes").add(bottle)
                    .addOnFailureListener {
                        Atlas.out("FAILED>>>>>>>>>")
                    }
            }
        }

        Await.pause(5000)


    }

    @Test
    fun addNote(){


    }

}