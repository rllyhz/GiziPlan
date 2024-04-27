package id.rllyhz.giziplan.domain.model.classification

open class WeightToHeightClassification(
    val classificationTypeSuffixId: Int
)

private const val SuffixId = 3
private const val ClassificationTypeSuffixId = SuffixId * 10

object SeverelyWasted : WeightToHeightClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToHeightClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 1

    override fun getClassificationName(): String =
        "gizi_buruk"
}

object Wasted : WeightToHeightClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToHeightClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 2

    override fun getClassificationName(): String =
        "gizi_kurang"
}

object GoodNutritionalStatus : WeightToHeightClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToHeightClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 3

    override fun getClassificationName(): String =
        "gizi_normal"
}

object PossibleRiskOfOverweight : WeightToHeightClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToHeightClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 4

    override fun getClassificationName(): String =
        "berisiko_gizi_lebih"
}

object Overweight : WeightToHeightClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToHeightClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 5

    override fun getClassificationName(): String =
        "gizi_lebih"
}

object Obese : WeightToHeightClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToHeightClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 6

    override fun getClassificationName(): String =
        "obesitas"
}