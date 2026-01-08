package com.example.bravo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import androidx.test.espresso.action.ViewActions.scrollTo

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
@RunWith(AndroidJUnit4::class)
class GameActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(GameActivity::class.java)

    @Test
    fun testGameScreenElementsLoad() {
        // Ellenőrizzük az alapvető szövegeket
        onView(withId(R.id.tvMode)).check(matches(isDisplayed()))
        onView(withId(R.id.tvCurrentPlayer)).check(matches(isDisplayed()))

        // Megnézzük, hogy az üveg (ImageView) ott van-e
        onView(withId(R.id.ivBottle)).check(matches(isDisplayed()))
    }

    @Test
    fun testGameFlow() {
        // 1. Megnyomjuk a "Következő játékos" gombot
        onView(withId(R.id.btnNextPlayer)).perform(click())

        // 2. Rákattintunk a "Felelsz" (Truth) gombra
        onView(withId(R.id.btnTruth)).perform(click())

        // 3. Ellenőrizzük, hogy dobott-e kérdést
        onView(withId(R.id.textView)).check(matches(not(withText(R.string.there_is_the_question))))
    }

    @Test
    fun testScrollingToBottomButton() {
        // Ellenőrizzük a legörgetést ,vagyis a legalsó elemhez megyünk
        onView(withId(R.id.btnSwitchMode))
            .perform(scrollTo(), click())

    }
}