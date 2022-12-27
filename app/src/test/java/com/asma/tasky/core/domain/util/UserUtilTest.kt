package com.asma.tasky.core.domain.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class UserUtilTest {

    @Test
    fun `name containing first name and last name returns two initials`() {
        val name = "Asma Smail"
        val initials = UserUtil.getInitials(name)
        val expected = "AS"
        assertEquals(expected, initials)
    }

    @Test
    fun `name containing only first name returns one initial`() {
        val name = "Asma"
        val initials = UserUtil.getInitials(name)
        val expected = "A"
        assertEquals(expected, initials)
    }

    @Test
    fun `name containing first name, last name and middle name returns two initials`() {
        val name = "mohamed yacine smail"
        val initials = UserUtil.getInitials(name)
        val expected = "MY"
        assertEquals(expected, initials)
    }

    @Test
    fun `empty name returns empty initials`() {
        val name = ""
        val initials = UserUtil.getInitials(name)
        val expected = ""
        assertEquals(expected, initials)
    }

    @Test
    fun `name with extra spaces returns correct initials`() {
        val name = "Asma  "
        val initials = UserUtil.getInitials(name)
        val expected = "A"
        assertEquals(expected, initials)
    }

    @Test
    fun `name with extra spaces between words returns correct initials`() {
        val name = "Asma  Smail "
        val initials = UserUtil.getInitials(name)
        val expected = "AS"
        assertEquals(expected, initials)
    }
}
