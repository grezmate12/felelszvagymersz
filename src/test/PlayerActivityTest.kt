package com.example.bravo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayersActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(PlayersActivity::class.java)

    private lateinit var activity: PlayersActivity

    @Before
    fun setUp() {
        activity = activityRule.activity
    }

    @Test
    fun testActivityExists() {
        assertNotNull(activity)
    }

    @Test
    fun testAddPlayer() {
        activity.players.add("Alice")
        assertEquals(1, activity.players.size)
    }

    @Test
    fun testEmptyPlayers() {
        activity.players.clear()
        assertEquals(0, activity.players.size)
    }
}