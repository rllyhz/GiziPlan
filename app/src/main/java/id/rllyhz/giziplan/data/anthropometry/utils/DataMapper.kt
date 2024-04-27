package id.rllyhz.giziplan.data.anthropometry.utils

import id.rllyhz.giziplan.data.anthropometry.model.PopulationRow
import id.rllyhz.giziplan.data.anthropometry.type.MeasuredValueType
import id.rllyhz.giziplan.data.anthropometry.type.PopulationValueType

fun List<List<Double>>.toPopulationRow(
    measuredValueType: MeasuredValueType,
    populationValueType: PopulationValueType
): List<PopulationRow> {
    val populationRows = arrayListOf<PopulationRow>()

    forEach {
        val newPopulationRow = PopulationRow(
            measuredValueType, populationValueType,
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