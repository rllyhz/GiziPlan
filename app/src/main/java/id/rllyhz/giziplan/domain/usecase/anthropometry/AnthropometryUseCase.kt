package id.rllyhz.giziplan.domain.usecase.anthropometry

import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.domain.model.zscore.ZScoreClassificationData
import id.rllyhz.giziplan.domain.model.zscore.ZScoreData

interface AnthropometryUseCase {
    /** Measure Z-Score value for weight to age
     * @param Double measuredWeight
     * @param Int age
     * @return [ZScoreData] Z-Score data
     */
    suspend fun measureZScoreForWeightToAge(
        measuredWeight: Double,
        age: Int,
        gender: Gender,
    ): ZScoreData

    /** Measure Z-Score value for height to age
     * @param Double measuredHeight
     * @param Int age
     * @return [ZScoreData] Z-Score data
     */
    suspend fun measureZScoreForHeightToAge(
        measuredHeight: Double,
        age: Int,
        gender: Gender,
    ): ZScoreData

    /** Measure Z-Score value for weight to height
     * @param Double measuredWeight
     * @param Double height
     * @return [ZScoreData] Z-Score data
     */
    suspend fun measureZScoreForWeightToHeight(
        measuredWeight: Double,
        height: Double,
        gender: Gender,
    ): ZScoreData

    /** Classify z-score value based off Anthropometry Standard
     * @param ZScoreData zScoreData
     * @return [ZScoreClassificationData] Z-Score classification data
     */
    suspend fun classifyZScore(zScoreData: ZScoreData): ZScoreClassificationData
}