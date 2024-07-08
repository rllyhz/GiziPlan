package id.rllyhz.giziplan.domain.utils

import id.rllyhz.giziplan.utils.fakeMenuList
import org.junit.Assert
import org.junit.Test

class UnorderedListUtilTest {

    private val menu = fakeMenuList

    @Test
    fun `successfully parse ingredients with no sub-label`() {
        val menuWithNoSubLabel = menu[0]

        val result = parseUnorderedStringListData(menuWithNoSubLabel.ingredients)

        Assert.assertFalse(result.hasSubLabel)
        Assert.assertTrue(result.data.isNotEmpty())
        Assert.assertTrue(result.data.first().listData.isNotEmpty())
        Assert.assertEquals(3, result.data.first().listData.count())
        Assert.assertEquals("ingredient1", result.data.first().listData.first())
        Assert.assertEquals("ingredient3", result.data.first().listData.last())
    }

    @Test
    fun `successfully parse ingredients with 2 sub-label`() {
        val menuWithSubLabel = menu[1]

        val result = parseUnorderedStringListData(menuWithSubLabel.ingredients)

        Assert.assertTrue(result.hasSubLabel)
        Assert.assertEquals(2, result.data.count())
        Assert.assertEquals("ingredient1", result.data.first().listData.first())
        Assert.assertEquals("ingredient2", result.data.first().listData.last())
        Assert.assertEquals("ingredient3", result.data.last().listData.first())
        Assert.assertEquals("ingredient5", result.data.last().listData.last())
    }

    @Test
    fun `successfully parse ingredients with 3 sub-label`() {
        val menuWithSubLabel = menu[2]

        val result = parseUnorderedStringListData(menuWithSubLabel.ingredients)

        Assert.assertTrue(result.hasSubLabel)
        Assert.assertEquals(3, result.data.count())
        Assert.assertEquals("ingredient1", result.data[0].listData.first())
        Assert.assertEquals("ingredient3", result.data[0].listData.last())
        Assert.assertEquals("ingredient4", result.data[1].listData.first())
        Assert.assertEquals("ingredient6", result.data[1].listData.last())
        Assert.assertEquals("ingredient7", result.data[2].listData.first())
    }
}