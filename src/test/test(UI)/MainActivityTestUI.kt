package com.example.bravo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityTestUI {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMenuButtonsAreVisible() {
        // A Play gomb látható-e és a szöveg is stimmel
        onView(withId(R.id.playButton))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.Play)))

        // Ellenőrizzük, hogy az Exit gomb látható-e
        onView(withId(R.id.exitButton))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.Exit)))
    }

    @Test
    fun testPlayButtonClick() {
        // Rákattintunk a Play gombra
        onView(withId(R.id.playButton)).perform(click())

        // Átvisz-e az új Layout-ra
        onView(withId(R.id.tvTitle))
            .check(matches(isDisplayed()))
    }
}
