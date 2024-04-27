package id.rllyhz.giziplan.domain.model.classification

open class HeightToAgeClassification(
    val classificationTypeSuffixId: Int
)

private const val SuffixId = 1
private const val ClassificationTypeSuffixId = SuffixId * 10

object SeverelyStunted : HeightToAgeClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        HeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 1

    override fun getClassificationName(): String =
        "sangat_pendek"
}

object Stunted : HeightToAgeClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        HeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 2

    override fun getClassificationName(): String =
        "pendek"
}

object NormalHeight : HeightToAgeClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        HeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 3

    override fun getClassificationName(): String =
        "normal"
}

object Tall : HeightToAgeClassification(ClassificationTypeSuffixId), ClassificationData {
    override fun getClassificationClassName(): String =
        HeightToAgeClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 4

    override fun getClassificationName(): String =
        "tinggi"
}