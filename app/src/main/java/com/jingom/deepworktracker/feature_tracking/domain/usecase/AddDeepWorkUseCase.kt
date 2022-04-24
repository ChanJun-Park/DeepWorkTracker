package com.jingom.deepworktracker.feature_tracking.domain.usecase

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.InvalidDeepWorkException
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import javax.inject.Inject

class AddDeepWorkUseCase @Inject constructor(
	private val deepWorkRepository: DeepWorkRepository
) {
	suspend operator fun invoke(deepWork: DeepWork): Int {
//		if (deepWork.title.isBlank()) {
//			throw InvalidDeepWorkException("Deep work title must not be empty")
//		}

		return deepWorkRepository.insertDeepWork(deepWork)
	}
}