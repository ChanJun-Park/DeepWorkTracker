package com.jingom.deepworktracker.feature_tracking.presentation

import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import java.time.LocalDate

data class DashBoardState(
	val deepWorks: List<DeepWork>,
	val deepWorkTimesOnDayInLastYear: LastYearDeepWorkData
)

data class LastYearDeepWorkData(
	val baseDate: LocalDate = LocalDateTimes.now().toLocalDate(),
	val deepWorkRecordMap: Map<LocalDate, DeepWorkTimesOnDay> = emptyMap()
)
