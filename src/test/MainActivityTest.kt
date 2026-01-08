package com.example.bravo

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testActivityExists() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertNotNull(activity)
            }
        }
    }

    @Test
    fun testPlayButtonExists() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val playButton = activity.findViewById<android.widget.Button>(R.id.playButton)
                assertNotNull(playButton)
            }
        }
    }

    @Test
    fun testExitButtonExists() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val exitButton = activity.findViewById<android.widget.Button>(R.id.exitButton)
                assertNotNull(exitButton)
            }
        }
    }
}