package com.jingom.deepworktracker.feature_tracking.domain.usecase

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import javax.inject.Inject

class DeleteDeepWorkUseCase @Inject constructor(
	private val deepWorkRepository: DeepWorkRepository
) {
	suspend operator fun invoke(deepWork: DeepWork) {
		deepWorkRepository.deleteDeepWork(deepWork)
	}
}