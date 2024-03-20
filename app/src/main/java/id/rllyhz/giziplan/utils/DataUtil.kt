package id.rllyhz.giziplan.utils

import id.rllyhz.giziplan.domain.model.MenuModel

object DataUtil {
    fun createDummyMenuData(amount: Int): List<MenuModel> {
        val menuList = arrayListOf<MenuModel>()

        for (i in 0..<amount) {
            val number = i + 1
            val newMenuData = MenuModel(
                i,
                "Menu $number",
                "Description $number",
                "menu_${number}_image.png",
            )

            menuList.add(newMenuData)
        }

        return menuList
    }
}