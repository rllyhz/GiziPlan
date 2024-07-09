package id.rllyhz.giziplan.domain.model

sealed class AgeCategory(
    val stringCategory: String,
    val stringDescription: String
) {
    // age 6-8 months
    data object A : AgeCategory("A", "6-8 Bulan")

    // age 9-11 months
    data object B : AgeCategory("B", "9-11 Bulan")

    // age 12-23 months
    data object C : AgeCategory("C", "12-23 Bulan")

    // age 24-60 months
    data object D : AgeCategory("D", "24-59 Bulan")
}