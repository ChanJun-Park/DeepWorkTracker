package com.jingom.deepworktracker.feature_tracking.presentation

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import java.time.LocalDate

data class DashBoardState(
	val deepWorks: List<DeepWork> = emptyList(),
	val deepWorkTimesOnDayInLastYear: Map<LocalDate, DeepWorkTimesOnDay> = emptyMap()
)
