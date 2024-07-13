package id.rllyhz.giziplan.domain.model.classification

open class InvalidZScoreClassification(
    val classificationTypeSuffixId: Int
)

private const val SuffixId = 4
private const val ClassificationTypeSuffixId = SuffixId * 10

object InvalidZScoreOfUnknown : InvalidZScoreClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        InvalidZScoreClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 1

    override fun getClassificationName(): String =
        "invalid_zscore_of_unknown"
}

object InvalidZScoreWeightToAge : InvalidZScoreClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        InvalidZScoreClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 2

    override fun getClassificationName(): String =
        "invalid_zscore_weight_to_age"
}

object InvalidZScoreHeightToAge : InvalidZScoreClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        InvalidZScoreClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 3

    override fun getClassificationName(): String =
        "invalid_zscore_height_to_age"
}

object InvalidZScoreWeightToHeight : InvalidZScoreClassification(ClassificationTypeSuffixId),
    ClassificationData {
    override fun getClassificationClassName(): String =
        InvalidZScoreClassification::javaClass.name

    override fun getClassificationId(): Int =
        classificationTypeSuffixId + 4

    override fun getClassificationName(): String =
        "invalid_zscore_weight_to_height"
}