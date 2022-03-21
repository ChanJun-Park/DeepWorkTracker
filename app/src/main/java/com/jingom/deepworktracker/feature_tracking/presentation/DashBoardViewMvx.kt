package com.jingom.deepworktracker.feature_tracking.presentation

import com.jingom.deepworktracker.common.screen.views.ObservableViewMvx
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import java.time.LocalDate

interface DashBoardViewMvx: ObservableViewMvx<DashBoardViewMvx.Listener> {
	interface Listener {

	}

	fun bindDeepWorks(deepWorks: List<DeepWork>)

	fun bindDeepWorkTimesOnDayInLastYear(deepWorkTimesOnDayInLastYear: Map<LocalDate, DeepWorkTimesOnDay>)
}