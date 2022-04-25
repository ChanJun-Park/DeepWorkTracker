package com.jingom.deepworktracker.feature_tracking.domain.usecase

import javax.inject.Inject

data class DeepWorkUseCases @Inject constructor(
	val addDeepWork: AddDeepWorkUseCase,
	val deleteDeepWork: DeleteDeepWorkUseCase,
	val getDeepWork: GetDeepWorkUseCase,
	val getDeepWorks: GetDeepWorksUseCase,
	val getDeepWorkTimesOnDayInLastYear: GetDeepWorkTimesOnDayInLastYearUseCase
)