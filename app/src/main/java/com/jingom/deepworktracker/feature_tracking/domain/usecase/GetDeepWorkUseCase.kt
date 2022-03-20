package com.jingom.deepworktracker.feature_tracking.domain.usecase

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import javax.inject.Inject

class GetDeepWorkUseCase @Inject constructor(
	private val deepWorkRepository: DeepWorkRepository
) {
	suspend operator fun invoke(id: Int): DeepWork? {
		return deepWorkRepository.getDeepWorkById(id)
	}
}