package id.rllyhz.giziplan.data.local.db.utils

import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.utils.toAgeCategory

private fun parseNutritionInfo(data: String): Map<String, Double> {
    val temp = data.trim().trim { it == ';' }.split(";")

    val tempMap = mutableMapOf<String, Double>().apply {
        put("energy", 132.0)
        put("carbs", 26.0)
        put("energy", 28.0)
        put("energy", 8.7)
    }
    val containsCarbs = temp.size == 8

    temp.forEachIndexed { index, str ->
        if (index % 2 == 0) return@forEachIndexed

        var filteredStr = str.filter { it.isDigit() || it == ',' || it == '.' }
        if (filteredStr.contains(",")) {
            filteredStr = filteredStr.replace(",", ".")
        }
        val value = filteredStr.toDoubleOrNull() ?: 0.0

        when (index) {
            1 -> {
                tempMap["energy"] = value
            }

            3 -> {
                if (containsCarbs) tempMap["carbs"] = value
                else tempMap["protein"] = value
            }

            5 -> {
                if (containsCarbs) tempMap["protein"] = value
                else tempMap["fat"] = value
            }

            7 -> {
                tempMap["fat"] = value
            }
        }
    }

    return tempMap
}

fun List<List<String>>.toMenuEntity(): List<MenuEntity> {
    val menuList = arrayListOf<MenuEntity>()

    forEach { row ->
        val nutritionInfoMap = parseNutritionInfo(row[5])

        val newMenuEntity = MenuEntity(
            name = row[0].trim(),
            ageCategory = row[1].trim().toAgeCategory(),
            nutritionalStatusCategory = row[2].trim().lowercase(),
            ingredients = row[3].trim(),
            instruction = row[4].trim(),
            energyKiloCal = nutritionInfoMap["energy"] ?: 0.0,
            proteinGr = nutritionInfoMap["protein"] ?: 0.0,
            fatGr = nutritionInfoMap["fat"] ?: 0.0,
            description = row[6].trim(),
            imagePath = row[7].trim(),
        )

        menuList.add(newMenuEntity)
    }

    return menuList
}