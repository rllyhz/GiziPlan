package id.rllyhz.giziplan.recommender

import id.rllyhz.giziplan.utils.fakeMenuCsvStringContent
import id.rllyhz.giziplan.utils.validationDataTest
import org.apache.commons.csv.CSVFormat
import java.io.StringReader

fun getMenu(): List<List<String>> {
    val data = arrayListOf<List<String>>()
    val parser = CSVFormat.DEFAULT.parse(StringReader(fakeMenuCsvStringContent))

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

        var status = nutritionalStatus
        if (nutritionalStatus.contains(",")) {
            status = nutritionalStatus.split(",").joinToString(" dan ")
        }

        val overview = "status gizi ${status.lowercase()} untuk umur ${age.lowercase()}"
        temp.add(overview)

        data.add(temp)
    }

    return data
}

fun getValidationData(): List<List<String>> {
    val data = arrayListOf<List<String>>()
    val parser = CSVFormat.DEFAULT.parse(StringReader(validationDataTest))

    parser.forEachIndexed { index, row ->
        if (index <= 0) return@forEachIndexed

        val temp = arrayListOf<String>()

        val childName = row[0].trim()
        temp.add(childName)

        val age = row[1].trim().lowercase()
        temp.add(age)

        val gender = row[2].trim()
        temp.add(gender)

        val height = row[3].trim()
        temp.add(height)

        val weight = row[4].trim()
        temp.add(weight)

        val nutritionalStatus = row[5].trim().replace("Gizi ", "").lowercase()
        temp.add(nutritionalStatus)

        val totalRecommendationMenu = row[6].trim()
        temp.add(totalRecommendationMenu)

        data.add(temp)
    }

    return data
}
