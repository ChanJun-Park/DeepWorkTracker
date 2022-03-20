package com.jingom.deepworktracker.feature_tracking.data.local

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.flow.Flow

interface DeepWorkLocalDataSource {
	fun getDeepWorks(): Flow<List<DeepWork>>

	suspend fun getDeepWorkById(id: Int): DeepWork?

	suspend fun insertDeepWork(deepWork: DeepWork)

	suspend fun deleteDeepWork(deepWork: DeepWork)
}