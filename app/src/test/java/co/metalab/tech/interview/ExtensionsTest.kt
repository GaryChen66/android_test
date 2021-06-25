package co.metalab.tech.interview

import co.metalab.tech.interview.common.prettyPrintId
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun prettyPrintId1() {
        val id = 1

        val expected = "#001"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }

    @Test
    fun prettyPrintId10() {
        val id = 10

        val expected = "#010"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }

    @Test
    fun prettyPrintId100() {
        val id = 100

        val expected = "#100"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }

    @Test
    fun prettyPrintIdNull() {
        val id = null

        val expected = "?"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }
}