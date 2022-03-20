package com.jingom.deepworktracker.feature_tracking.domain.usecase

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDeepWorksUseCase @Inject constructor(
	private val deepWorkRepository: DeepWorkRepository
) {
	operator fun invoke(): Flow<List<DeepWork>> {
		return deepWorkRepository.getDeepWorks().map { deepWorks ->
			deepWorks.sortedByDescending { it.lastWorkingDateTime }
		}
	}
}