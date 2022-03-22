package com.jingom.deepworktracker.feature_tracking.presentation

import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import java.time.LocalDate

data class DashBoardState(
	val deepWorks: List<DeepWork> = emptyList(),
	val deepWorkTimesOnDayInLastYear: Pair<LocalDate, Map<LocalDate, DeepWorkTimesOnDay>> = Pair(LocalDateTimes.now().toLocalDate(), emptyMap())
)
