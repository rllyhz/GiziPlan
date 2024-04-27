package id.rllyhz.giziplan.data.anthropometry.utils

import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.ReferenceValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

fun List<List<String>>.toPopulationRow(
    referenceValueType: ReferenceValueType,
    populationValueType: PopulationValueType
): List<PopulationRow> {
    val populationRows = arrayListOf<PopulationRow>()

    forEach {
        val newPopulationRow = PopulationRow(
            referenceValueType, populationValueType,
            it[0],
            it[1],
            it[2],
            it[3],
            it[4],
            it[5],
            it[6],
            it[7],
        )

        populationRows.add(newPopulationRow)
    }

    return populationRows
}