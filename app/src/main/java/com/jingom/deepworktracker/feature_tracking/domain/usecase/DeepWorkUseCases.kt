package com.jingom.deepworktracker.feature_tracking.domain.usecase

data class DeepWorkUseCases(
	val addDeepWork: AddDeepWorkUseCase,
	val deleteDeepWork: DeleteDeepWorkUseCase,
	val getDeepWork: GetDeepWorkUseCase,
	val getDeepWorks: GetDeepWorksUseCase,
	val getDeepWorkTimesOnDayInLastYear: GetDeepWorkTimesOnDayInLastYearUseCase
)