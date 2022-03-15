package xyz.cloudcoffee.atlas.screens.notes

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.framework.TestCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.cloudcoffee.atlas.R
import xyz.cloudcoffee.atlas.TestEnv
import xyz.cloudcoffee.atlas.screens.login.LoginActivity

@LargeTest
@RunWith(AndroidJUnit4::class)
class NotesActivityTest : TestCase() {

    private lateinit var scenario: ActivityScenario<NotesActivity>

    @Before
    fun setup(){
        scenario = ActivityScenario.launch(NotesActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

    }

    @Test
    fun printMe(){
        TestEnv.awaitWork<Int>("Running Back Test"){
            GlobalScope.launch {
                TestEnv.out("Running a tester")
                delay(200)
                it.post(0)
            }
        }


        onView(withId(R.id.btnCrashMe)).perform(click())




    }
}