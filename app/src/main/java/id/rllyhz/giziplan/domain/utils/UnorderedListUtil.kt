package id.rllyhz.giziplan.domain.utils

data class UnorderedListData<T>(
    val label: String, val listData: List<T>
)

data class ParsedUnorderedListData<T>(
    val data: List<UnorderedListData<T>>,
    val hasSubLabel: Boolean,
)

private fun getListData(data: List<String>, startIndex: Int, endIndex: Int): List<String> {
    val result = arrayListOf<String>()

    for (i in startIndex..endIndex) {
        result.add(data[i].trim())
    }

    return result
}

fun parseUnorderedStringListData(data: String): ParsedUnorderedListData<String> {
    val validatedData = data.trim { it == ';' }
    val tempData = validatedData.split(";").map { it.trim() }
    val first = tempData.first()
    val shouldHave2SubLabelRegex = Regex("\\d,\\d", setOf(RegexOption.IGNORE_CASE))
    val shouldHave3SubLabelRegex2 = Regex("\\d,\\d,\\d", setOf(RegexOption.IGNORE_CASE))
    val hasSubLevel =
        shouldHave2SubLabelRegex.matches(first) or shouldHave3SubLabelRegex2.matches(first)

    val result = arrayListOf<UnorderedListData<String>>()

    if (!hasSubLevel) {
        result.add(
            UnorderedListData("", tempData)
        )

        return ParsedUnorderedListData(
            result, false
        )
    }

    // ================================
    // Parse Sub Label
    val listDataWithLabels = arrayListOf<UnorderedListData<String>>()
    val subLabelInfo = tempData.first().split(",").map { it.trim().toInt() }
    val subLabelNames =
        (tempData.getOrNull(1) ?: "Label1, Label2, Label3").split(",").map { it.trim() }

    // with 2 label
    if (shouldHave2SubLabelRegex.matches(first)) {
        var tempStartIndex = 0
        var tempEndIndex = 0

        for (i in 0..1) {
            val startIndex = if (i == 0) {
                tempStartIndex = 2
                tempStartIndex
            } else {
                tempStartIndex = tempEndIndex + 1
                tempStartIndex
            }

            tempEndIndex =
                if (i == 0) startIndex + (subLabelInfo[1]) - 1
                else tempData.lastIndex
            val endIndex = tempEndIndex

            result.add(
                UnorderedListData(subLabelNames[i], getListData(tempData, startIndex, endIndex))
            )
        }

        return ParsedUnorderedListData(
            result, true
        )
    }

    // with 3 label
    var tempStartIndex = 0
    var tempEndIndex = 0

    // 3,2,5
    for (i in 0..2) {
        val startIndex = if (i == 0) {
            tempStartIndex = 2
            tempStartIndex
        } else {
            tempStartIndex = tempEndIndex + 1
            tempStartIndex
        }

        tempEndIndex =
            when (i) {
                0 -> startIndex + (subLabelInfo[1]) - 1
                1 -> tempEndIndex + (subLabelInfo[2])
                else -> tempData.lastIndex
            }
        val endIndex = tempEndIndex

        result.add(
            UnorderedListData(subLabelNames[i], getListData(tempData, startIndex, endIndex))
        )
    }

    return ParsedUnorderedListData(
        result, true
    )
}