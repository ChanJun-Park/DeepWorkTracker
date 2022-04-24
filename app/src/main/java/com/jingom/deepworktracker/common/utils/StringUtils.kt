package com.jingom.deepworktracker.common.utils

fun String.getLimitedLinesText(maxLine: Int): String {
	val lineChangeCount = count { it == '\n' }

	return if (lineChangeCount >= maxLine) {
		val index = ordinalIndexOf('\n', maxLine)
		if (index == -1) {
			this
		} else {
			substring(0, index)
		}
	} else {
		this
	}
}

fun String.ordinalIndexOf(c: Char, ordinal: Int): Int {
	var count = 0
	forEachIndexed { index, character ->
		if (character == c) {
			count++
		}

		if (count == ordinal) {
			return index
		}
	}

	return -1
}

