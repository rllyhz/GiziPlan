package id.rllyhz.giziplan.domain.model.classification

open class WeightToAgeClassification(
    val classificationTypeSuffixId: Int
)

private const val SuffixId = 2
private const val ClassificationTypeSuffixId = SuffixId * 10

object SeverelyUnderweight : WeightToAgeClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 1

    override fun getClassificationName(): String =
        "berat_badan_sangat_kurang"
}

object Underweight : WeightToAgeClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 2

    override fun getClassificationName(): String =
        "berat_badan_kurang"
}

object NormalWeight : WeightToAgeClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 3

    override fun getClassificationName(): String =
        "berat_badan_normal"
}

object RiskOfOverweight : WeightToAgeClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        WeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 4

    override fun getClassificationName(): String =
        "risiko_berat_badan_lebih"
}