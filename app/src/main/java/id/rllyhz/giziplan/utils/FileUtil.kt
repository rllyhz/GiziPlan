package id.rllyhz.giziplan.utils

import android.content.Context
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.io.RawFilesReader

fun readCSVFileInRawFolder(
    context: Context, resId: Int
): String = RawFilesReader(context).readFileByResId(resId)

fun getPopulationTableDataByCSVFile(context: Context, csvResId: Int): List<List<Double>> {
    val population = arrayListOf<List<Double>>()

    val csvStringContent = readCSVFileInRawFolder(context, csvResId)
    val lines = csvStringContent.lines()

    // remove header (line 1 and 2)
    lines.forEachIndexed { index, line ->
        if (index <= 1) return@forEachIndexed

        val cell = line.split(",").map {
            it.trim().filter { char -> char.isDigit() || char == '.' }.toDouble()
        }

        population.add(cell)
    }

    return population
}

fun getWeightToAgePopulationTableData(context: Context, gender: Gender): List<List<Double>> =
    if (gender == Gender.Male)
        getPopulationTableDataByCSVFile(context, R.raw.bb_menurut_umur_laki_laki)
    else
        getPopulationTableDataByCSVFile(context, R.raw.bb_menurut_umur_perempuan)

fun getHeightToAgePopulationTableData(context: Context, gender: Gender): List<List<Double>> =
    if (gender == Gender.Male)
        getPopulationTableDataByCSVFile(context, R.raw.pb_atau_tb_menurut_umur_laki_laki)
    else
        getPopulationTableDataByCSVFile(context, R.raw.pb_atau_tb_menurut_umur_perempuan)

fun getWeightToHeightPopulationTableData(context: Context, gender: Gender): List<List<Double>> =
    if (gender == Gender.Male)
        getPopulationTableDataByCSVFile(context, R.raw.bb_menurut_pb_atau_tb_laki_laki)
    else
        getPopulationTableDataByCSVFile(context, R.raw.bb_menurut_pb_atau_tb_perempuan)
