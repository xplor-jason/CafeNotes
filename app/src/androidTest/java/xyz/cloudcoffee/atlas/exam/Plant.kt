package xyz.cloudcoffee.atlas.exam

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

class Food {
    val runner : (msg : String, pop : String) -> Int =  { s: String, s1: String ->
        val rand = Random(0)
        rand.nextInt()
    }
}


@RunWith(AndroidJUnit4::class)
class Plant {
    @Test
    fun plantMe(){
        val pizza = Food()
        val popcorn = pizza.runner("sd", "Sd")

    }
}