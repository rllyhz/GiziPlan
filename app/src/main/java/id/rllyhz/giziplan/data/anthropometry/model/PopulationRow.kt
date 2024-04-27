package id.rllyhz.giziplan.data.anthropometry.model

import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

data class PopulationRow(
    val referenceValueType: ReferenceValueType,
    val populationValueType: PopulationValueType,
    val referenceValue: Double,
    val min3SD: Double,
    val min2SD: Double,
    val min1SD: Double,
    val median: Double,
    val plus1SD: Double,
    val plus2SD: Double,
    val plus3SD: Double,
)
