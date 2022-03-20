package com.jingom.deepworktracker.feature_tracking.domain.repository

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.flow.Flow

interface DeepWorkRepository {
	fun getDeepWorks(): Flow<List<DeepWork>>

	suspend fun getDeepWorkById(id: Int): DeepWork?

	suspend fun insertDeepWork(deepWork: DeepWork)

	suspend fun deleteDeepWork(deepWork: DeepWork)
}