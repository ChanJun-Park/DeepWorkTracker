package com.jingom.deepworktracker.feature_tracking.domain.model

import androidx.annotation.ColorRes
import com.jingom.deepworktracker.R
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

		return DeepWorkLevel.LEVEL4
	}

	enum class DeepWorkLevel(val deepWorkTimeInHour: Int, @ColorRes val colorId: Int, @ColorRes val containerColorId: Int) {
		LEVEL0(0, R.color.deepwork_green_50, R.color.deepwork_record_container1),
		LEVEL1(1, R.color.deepwork_green_200, R.color.deepwork_record_container2),
		LEVEL2(2, R.color.deepwork_green_300, R.color.deepwork_record_container3),
		LEVEL3(3, R.color.deepwork_green_400, R.color.deepwork_record_container4),
		LEVEL4(4, R.color.deepwork_green_500, R.color.deepwork_record_container5)
	}
}