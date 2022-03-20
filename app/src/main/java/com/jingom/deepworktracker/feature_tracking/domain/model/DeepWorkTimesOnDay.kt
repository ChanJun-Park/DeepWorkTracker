package com.jingom.deepworktracker.feature_tracking.domain.model

import java.time.Duration
import java.time.LocalDate

data class DeepWorkTimesOnDay(
	val date: LocalDate,
	val totalDeepWorkTime: Long
)  {
	private fun getTotalDeepWorkTimeInHour(): Long = Duration.ofSeconds(totalDeepWorkTime).toHours()

	fun getDeepWorkLevel(): DeepWorkLevel {
		for (deepWorkLevel in DeepWorkLevel.values()) {
			if (getTotalDeepWorkTimeInHour() < deepWorkLevel.deepWorkTimeInHour) {
				return deepWorkLevel
			}
		}

		return DeepWorkLevel.LEVEL5
	}

	enum class DeepWorkLevel(val deepWorkTimeInHour: Int) {
		LEVEL0(0),
		LEVEL1(1),
		LEVEL2(2),
		LEVEL3(3),
		LEVEL4(4),
		LEVEL5(5)
	}
}