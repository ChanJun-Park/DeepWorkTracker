package com.jingom.deepworktracker.feature_tracking.domain.usecase

data class DeepWorkUseCases(
	val addDeepWorkUseCase: AddDeepWorkUseCase,
	val deleteDeepWorkUseCase: DeleteDeepWorkUseCase,
	val getDeepWorkUseCase: GetDeepWorkUseCase,
	val getDeepWorksUseCase: GetDeepWorksUseCase,
	val getDeepWorkTimesOnDayInLastYearUseCase: GetDeepWorkTimesOnDayInLastYearUseCase
)