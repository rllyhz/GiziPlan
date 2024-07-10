package id.rllyhz.giziplan.recommender

import kotlin.math.log
import kotlin.math.sqrt

object Recommender {
    // calculate TF (Term Frequency)
    private fun calculateTF(document: String): Map<String, Double> {
        val tfMap = mutableMapOf<String, Double>()
        val words = document.split("\\s+".toRegex())
        val totalWords = words.size.toDouble()

        for (word in words) {
            val count = tfMap.getOrDefault(word, 0.0)
            tfMap[word] = count + (1.0 / totalWords)
        }

        return tfMap
    }

    // Calculate IDF (Inverse Document Frequency)
    private fun calculateIDF(documents: List<String>): Map<String, Double> {
        val idfMap = mutableMapOf<String, Double>()
        val totalDocuments = documents.size.toDouble()

        for (document in documents) {
            val words = document.split("\\s+".toRegex()).distinct()
            for (word in words) {
                val count = idfMap.getOrDefault(word, 0.0)
                idfMap[word] = count + 1.0
            }
        }

        for (word in idfMap.keys) {
            idfMap[word] = log(totalDocuments / (idfMap[word] ?: 1.0), 10.0) + 1.0
        }

        return idfMap
    }

    // Calculate TF-IDF
    private fun calculateTFIDF(documents: List<String>): List<Map<String, Double>> {
        val tfidfList = mutableListOf<Map<String, Double>>()
        val idfMap = calculateIDF(documents)

        for (document in documents) {
            val tfMap = calculateTF(document)
            val tfidfMap = mutableMapOf<String, Double>()

            for ((word, tf) in tfMap) {
                tfidfMap[word] = tf * (idfMap[word] ?: 1.0)
            }

            tfidfList.add(tfidfMap)
        }

        return tfidfList
    }

    // Calculate cosine similarity between two vectors of TF-IDF
    private fun cosineSimilarity(vec1: Map<String, Double>, vec2: Map<String, Double>): Double {
        var dotProduct = 0.0
        var mag1 = 0.0
        var mag2 = 0.0

        for ((term, tfidf) in vec1) {
            dotProduct += tfidf * (vec2[term] ?: 0.0)
            mag1 += tfidf * tfidf
        }

        for ((_, tfidf) in vec2) {
            mag2 += tfidf * tfidf
        }

        if (mag1 == 0.0 || mag2 == 0.0) {
            return 0.0
        }

        return dotProduct / (sqrt(mag1) * sqrt(mag2))
    }

    fun getRecommendation(
        nutritionStatus: String, ageInMonths: Int, overview: List<String>, n: Int = 3
    ): List<Int> {
        val query =
            "status gizi " + parseNutritionStatus(nutritionStatus) + " untuk " + parseAge(
                ageInMonths
            )

        val tfidfList = calculateTFIDF(overview)
        val queryTFIDF = calculateTFIDF(listOf(query))[0]

        // index -> score
        val scoredByDocumentIndex = mutableListOf<Pair<Int, Double>>()

        for ((index, _) in overview.withIndex()) {
            val menuTFIDF = tfidfList[index]
            val score = cosineSimilarity(queryTFIDF, menuTFIDF)
            scoredByDocumentIndex.add(index to score)
        }

        // sort by the highest cosine similarity
        scoredByDocumentIndex.sortByDescending { it.second }

        return scoredByDocumentIndex.map { it.first }.take(n)
    }

    private fun parseNutritionStatus(status: String): String = when (status.lowercase()) {
        "obesitas" -> "lebih dan obesitas"
        else -> status
    }

    fun parseAge(age: Int): String = when {
        age <= 8 -> "6-8 bulan"
        age <= 11 -> "9-11 bulan"
        age <= 23 -> "12-23 bulan"
        age > 23 -> "24-59 bulan"
        else -> "6-8 bulan"
    }
}