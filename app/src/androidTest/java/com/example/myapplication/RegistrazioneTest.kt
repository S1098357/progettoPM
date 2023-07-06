package com.example.myapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class RegistrazioneTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Registrazione::class.java)
    @Test
    fun testRegistrazione() {
        onView(withId(R.id.imageView6)).check(matches(isDisplayed()))
    }
}