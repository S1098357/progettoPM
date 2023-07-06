package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testUser() {
        val user=User("prova","prova","prova", "prova")
        assertEquals(user.name, "prova")
        assertEquals(user.email, "prova")
        assertEquals(user.password, "prova")
        assertEquals(user.uid, "prova")
    }

    @Test
    fun testMessage(){
        val messaggio=Message("ciao","io")
        assertEquals(messaggio.senderId, "io")
        assertEquals(messaggio.message, "ciao")
    }

    @Test
    fun testPropertyValue(){
        val propertyValue=PropertyValue("prova","prova","prova","prova","prova","prova")
        assertEquals(propertyValue.addressTextView, "prova")
        assertEquals(propertyValue.priceTextView, "prova")
        assertEquals(propertyValue.propertyTypeTextView, "prova")
        assertEquals(propertyValue.propertyCode, "prova")
        assertEquals(propertyValue.roomsTextView, "prova")
    }
}