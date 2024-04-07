package id.rllyhz.giziplan.utils

import org.junit.Assert
import org.junit.Test

class TypeMapperTest {

    @Test
    fun `successfully convert string menu ids to list of int`() {
        val length = 4
        val menuIds = "1,2,,4,5,,"

        val result = menuIds.toIntList()

        Assert.assertEquals(result.count(), length)
        Assert.assertEquals(result.first(), 1)
        Assert.assertEquals(result.last(), 5)
    }
}