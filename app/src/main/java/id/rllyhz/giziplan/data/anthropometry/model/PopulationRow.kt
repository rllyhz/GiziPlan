package id.rllyhz.giziplan.data.anthropometry.model

import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

data class PopulationRow(
    val referenceValueType: ReferenceValueType,
    val populationValueType: PopulationValueType,
    val referenceValue: String,
    val min3SD: String,
    val min2SD: String,
    val min1SD: String,
    val median: String,
    val plus1SD: String,
    val plus2SD: String,
    val plus3SD: String,
)
