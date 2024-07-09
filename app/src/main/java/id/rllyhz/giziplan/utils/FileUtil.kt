package id.rllyhz.giziplan.utils

import android.content.Context
import id.rllyhz.giziplan.R
import id.rllyhz.giziplan.data.anthropometry.type.Gender
import id.rllyhz.giziplan.io.RawFilesReader
import org.apache.commons.csv.CSVFormat
import java.io.BufferedReader
import java.io.InputStreamReader

fun readCSVFileInRawFolder(
    context: Context, resId: Int
): String = RawFilesReader(context).readFileByResId(resId)

fun getMenuDataByCSVFile(context: Context): List<List<String>> {
    val data = arrayListOf<List<String>>()
    val reader =
        BufferedReader(InputStreamReader(context.resources.openRawResource(R.raw.menu_full)))
    val parser = CSVFormat.DEFAULT.parse(reader)

    parser.forEachIndexed { index, row ->
        if (index <= 0) return@forEachIndexed

        val temp = arrayListOf<String>()

        val name = row[0].trim()
        temp.add(name)

        val age = row[1].trim().lowercase()
        temp.add(age)

        val nutritionalStatus = row[2].trim()
        temp.add(nutritionalStatus)

        val ingredients = row[3].trim().trim { it == ';' }
        temp.add(ingredients)

        val instructions = row[4].trim().trim { it == ';' }
        temp.add(instructions)

        val nutritionInfo = row[5].trim().trim { it == ';' }
        temp.add(nutritionInfo)

        val notes = row[6].trim().trim { it == ';' }
        temp.add(notes)

        data.add(temp)
    }

    return data
}

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
    context: Context, gender: Gender
): List<List<String>> = if (gender == Gender.Male) getPopulationTableDataByCSVFile(
    context, R.raw.lklk_bb_per_pb_0_24
)
else getPopulationTableDataByCSVFile(context, R.raw.perem_bb_per_pb_0_24)

fun getWeightToHeightGreaterThan24PopulationTableData(
    context: Context, gender: Gender
): List<List<String>> = if (gender == Gender.Male) getPopulationTableDataByCSVFile(
    context, R.raw.lklk_bb_per_tb_24_60
)
else getPopulationTableDataByCSVFile(context, R.raw.perem_bb_per_tb_24_60)

fun getMenuData(
    context: Context
): List<List<String>> = getMenuDataByCSVFile(context)
