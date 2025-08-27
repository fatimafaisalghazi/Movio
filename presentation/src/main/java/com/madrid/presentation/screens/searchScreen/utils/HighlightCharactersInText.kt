package com.madrid.presentation.screens.searchScreen.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle


fun highlightCharactersInText(
    fullText: String,
    query: String,
    matchColor: Color,
    normalColor: Color,
    textStyle: TextStyle
): AnnotatedString {
    val builder = AnnotatedString.Builder()

    if (query.isBlank()) {
        builder.pushStyle(textStyle.copy(color = normalColor).toSpanStyle())
        builder.append(fullText)
        return builder.toAnnotatedString()
    } else {
        val numberSet = mutableSetOf<Int>()
        query.trim()
            .lowercase()
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .forEach { pureQuery ->
                var searchIndex = 0

                while (true) {
                    val foundIndex = fullText.lowercase().indexOf(pureQuery, searchIndex)
                    if (foundIndex == -1) break

                    for (i in foundIndex until foundIndex + pureQuery.length) {
                        numberSet.add(i)
                    }

                    searchIndex = foundIndex + pureQuery.length
                }
            }

        builder.pushStyle(textStyle.copy(color = normalColor).toSpanStyle())
        builder.append(fullText)

        numberSet.toRanges().forEach { range ->
            builder.addStyle(
                textStyle.copy(color = matchColor).toSpanStyle(),
                start = range.first,
                end = range.last + 1
            )
        }
        return builder.toAnnotatedString()

    }
}

private fun Set<Int>.toRanges(): List<IntRange> {
    if (isEmpty()) return emptyList()

    val sorted = this.sorted()
    val ranges = mutableListOf<IntRange>()

    var rangeStart = sorted.first()
    var prev = sorted.first()

    for (i in 1 until sorted.size) {
        val current = sorted[i]
        if (current != prev + 1) {
            ranges.add(rangeStart..prev)
            rangeStart = current
        }
        prev = current
    }
    ranges.add(rangeStart..prev)

    return ranges
}