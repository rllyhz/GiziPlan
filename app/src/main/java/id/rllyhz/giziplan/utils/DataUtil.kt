package id.rllyhz.giziplan.utils

import id.rllyhz.giziplan.domain.model.AgeCategory
import id.rllyhz.giziplan.domain.model.MenuModel
import id.rllyhz.giziplan.domain.model.classification.ClassificationData
import id.rllyhz.giziplan.domain.model.classification.GoodNutritionalStatus
import id.rllyhz.giziplan.domain.model.classification.Obese
import id.rllyhz.giziplan.domain.model.classification.Overweight
import id.rllyhz.giziplan.domain.model.classification.PossibleRiskOfOverweight
import id.rllyhz.giziplan.domain.model.classification.SeverelyWasted
import id.rllyhz.giziplan.domain.model.classification.Wasted
import kotlin.random.Random

/**
 * Round the [Double] value to be either x.0 or x.5
 */
fun Double.roundToNearestHalf(): Double {
    val remainder = this % 1
    return when {
        remainder <= 0.25 -> this - remainder
        remainder <= 0.75 -> this - remainder + 0.5
        else -> this - remainder + 1
    }
}

fun randomNum(min: Int, max: Int, exclude: Boolean = false) =
    if (exclude) (min..<max).random()
    else (min..max).random()

fun randomAge(): Int = randomNum(12, 60)

fun randomHeight(): Double = Random.nextDouble(12.0, 120.0)

fun randomWeight(): Double = Random.nextDouble(3.0, 7.0)

fun randomEnergy(): Double = Random.nextDouble(1.0, 5.0)
fun randomFat(): Double = Random.nextFloat().toDouble()
fun randomProtein(): Double = Random.nextDouble(3.0, 12.0)

fun getRandomNutritionalStatusCategory(): ClassificationData =
    when (randomNum(1, 5)) {
        1 -> SeverelyWasted
        2 -> Wasted
        3 -> GoodNutritionalStatus
        4 -> PossibleRiskOfOverweight
        5 -> Overweight
        else -> Obese
    }

fun getRandomAgeCategory(): AgeCategory =
    when (randomNum(1, 4)) {
        1 -> AgeCategory.A
        2 -> AgeCategory.B
        3 -> AgeCategory.C
        4 -> AgeCategory.D
        else -> AgeCategory.A
    }

fun createDummyMenuData(amount: Int = 20): List<MenuModel> {
    val menuList = arrayListOf<MenuModel>()

    for (i in 0..<amount) {
        val number = i + 1
        val newMenuData = MenuModel(
            i,
            "Menu $number",
            "Ingredients menu $number",
            "Instruction menu $number",
            "tinggi, normal, baik",
            "menu_${number}_image.png",
            1.4,
            12.0,
            0.3,
            "Description menu $number"
        )

        menuList.add(newMenuData)
    }

    return menuList
}

fun String.capitalize(): String = this.replaceFirstChar {
    it.uppercase()
}