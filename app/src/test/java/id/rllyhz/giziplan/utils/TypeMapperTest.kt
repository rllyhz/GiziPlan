package id.rllyhz.giziplan.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class TypeMapperTest {

    @Test
    fun `successfully convert string menu ids to list of int`() {
        val length = 4
        val menuIds = "1,2,,4,5,,"

        val result = menuIds.toIntList()

        assertEquals(result.count(), length)
        assertEquals(result.first(), 1)
        assertEquals(result.last(), 5)
    }

    @Test
    fun `successfully convert Long-typed time values to Numerical Date String values`() {
        val milliseconds = 1720944910837L
        val expectedFormattedDate = "14-07-2024 15:15:10"

        val actualFormattedDate = milliseconds.toDateNumericalString()

        assertEquals(expectedFormattedDate, actualFormattedDate)
    }

    @Test
    fun `successfully convert Numerical Date String values to Date String values`() {
        val exampleDate = "14-07-2024 15:15:10"
        val expectedDateString = "14 Juli 2024 - 15:15:10"

        val actualResult = exampleDate.toDateString()

        assertEquals(expectedDateString, actualResult)


        val exampleDate2 = "05-12-2027 04:20:32"
        val expectedDateString2 = "05 Desember 2027 - 04:20:32"

        val actualResult2 = exampleDate2.toDateString()

        assertEquals(expectedDateString2, actualResult2)


        val exampleError = "05-12-202704:20:32"
        val expectedWrongResult = "Invalid Numerical Date string"

        val actualWrongResult = exampleError.toDateString()

        assertEquals(expectedWrongResult, actualWrongResult)
    }
}