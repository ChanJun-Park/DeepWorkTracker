package com.jingom.deepworktracker.feature_tracking.data.local

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface DeepWorkLocalDataSource {
	fun getDeepWorks(): Flow<List<DeepWork>>

	fun getDeepWorksInRange(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Flow<List<DeepWork>>

	suspend fun getDeepWorkById(id: Int): DeepWork?

	suspend fun insertDeepWork(deepWork: DeepWork): Int

	suspend fun deleteDeepWork(deepWork: DeepWork)
}