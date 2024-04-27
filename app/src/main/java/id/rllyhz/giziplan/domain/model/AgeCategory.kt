package id.rllyhz.giziplan.domain.model

sealed class AgeCategory(
    val stringCategory: String
) {
    // age 6-8 months
    data object A : AgeCategory("A")

    // age 9-11 months
    data object B : AgeCategory("B")

    // age 12-23 months
    data object C : AgeCategory("C")

    // age 24-60 months
    data object D : AgeCategory("D")
}