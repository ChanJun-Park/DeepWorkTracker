package com.jingom.deepworktracker.feature_tracking.domain.repository

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface DeepWorkRepository {
	fun getDeepWorks(): Flow<List<DeepWork>>

	fun getDeepWorksInLastYear(baseDateTime: LocalDateTime): Flow<List<DeepWork>>

	suspend fun getDeepWorkById(id: Int): DeepWork?

	suspend fun insertDeepWork(deepWork: DeepWork): Long

	suspend fun deleteDeepWork(deepWork: DeepWork)
}