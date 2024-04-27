package id.rllyhz.giziplan.utils

import android.content.Context
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.io.RawFilesReader

fun readCSVFileInRawFolder(
    context: Context, resId: Int
): String = RawFilesReader(context).readFileByResId(resId)

fun getPopulationTableDataByCSVFile(context: Context, csvResId: Int): List<List<String>> {
    val csvStringContent = readCSVFileInRawFolder(context, csvResId)
    return parseCSVContentToPopulationDataTable(csvStringContent)
}

fun parseCSVContentToPopulationDataTable(content: String): List<List<String>> {
    val populationDataTable = arrayListOf<List<String>>()
    val lines = content.lines()

    // remove header (line 1 and 2)
    lines.forEachIndexed { index, line ->
        if (index <= 1) return@forEachIndexed

        val cell = line.split(",").map {
            it.trim().filter { char -> char.isDigit() || char == '.' }
        }

        populationDataTable.add(cell)
    }

    return populationDataTable
}

fun getWeightToAgePopulationTableData(context: Context, gender: Gender): List<List<String>> =
    if (gender == Gender.Male) getPopulationTableDataByCSVFile(
        context, R.raw.lklk_bb_per_u_0_60
    )
    else getPopulationTableDataByCSVFile(context, R.raw.perem_bb_per_u_0_60)

fun getHeightToAgePopulationTableData(context: Context, gender: Gender): List<List<String>> =
    if (gender == Gender.Male) getPopulationTableDataByCSVFile(
        context, R.raw.lklk_pb_tb_per_u_0_60
    )
    else getPopulationTableDataByCSVFile(context, R.raw.perem_pb_tb_per_u_0_60)

fun getWeightToHeightLessThan24PopulationTableData(
    context: Context,
    gender: Gender
): List<List<String>> =
    if (gender == Gender.Male) getPopulationTableDataByCSVFile(
        context, R.raw.lklk_bb_per_pb_0_24
    )
    else getPopulationTableDataByCSVFile(context, R.raw.perem_bb_per_pb_0_24)

fun getWeightToHeightGreaterThan24PopulationTableData(
    context: Context,
    gender: Gender
): List<List<String>> =
    if (gender == Gender.Male) getPopulationTableDataByCSVFile(
        context, R.raw.lklk_bb_per_tb_24_60
    )
    else getPopulationTableDataByCSVFile(context, R.raw.perem_bb_per_tb_24_60)
