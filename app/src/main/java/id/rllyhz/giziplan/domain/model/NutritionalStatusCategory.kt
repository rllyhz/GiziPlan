package id.rllyhz.giziplan.domain.model

sealed class NutritionalStatusCategory(
    val stringCategory: String = "gizi_normal"
) {
    data object SeverelyWasted : NutritionalStatusCategory("gizi_buruk")

    data object Wasted : NutritionalStatusCategory("gizi_kurang")

    data object Normal : NutritionalStatusCategory("gizi_normal")

    object PossibleRiskOfOverweight : NutritionalStatusCategory("gizi_lebih")

    data object Overweight : NutritionalStatusCategory("obesitas")
}