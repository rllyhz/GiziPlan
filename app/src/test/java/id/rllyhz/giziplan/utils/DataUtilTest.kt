package id.rllyhz.giziplan.utils

import org.junit.Assert
import org.junit.Test

class DataUtilTest {

    @Test
    fun `successfully randomize number between min and max (included)`() {
        val result1 = randomNum(1, 5)
        Assert.assertTrue(result1 >= 1)
        Assert.assertTrue(result1 <= 5)
        Assert.assertFalse(result1 < 1)
        Assert.assertFalse(result1 > 5)

        val result2 = randomNum(4, 5)
        Assert.assertTrue(result2 >= 4)
        Assert.assertTrue(result2 <= 5)
        Assert.assertFalse(result2 < 4)
        Assert.assertFalse(result2 > 5)
    }

    @Test
    fun `successfully randomize number between min and max (excluded)`() {
        val result1 = randomNum(1, 5, true)
        Assert.assertTrue(result1 >= 1)
        Assert.assertTrue(result1 < 5)
        Assert.assertFalse(result1 < 1)
        Assert.assertFalse(result1 > 5)

        val result2 = randomNum(4, 5, true)
        Assert.assertTrue(result2 >= 4)
        Assert.assertTrue(result2 < 5)
        Assert.assertFalse(result2 < 4)
        Assert.assertFalse(result2 > 5)
    }

    @Test
    fun `create dummy menu model`() {
        val length = 10
        val result = createDummyMenuData(length)

        Assert.assertEquals(length, result.count())
    }
}