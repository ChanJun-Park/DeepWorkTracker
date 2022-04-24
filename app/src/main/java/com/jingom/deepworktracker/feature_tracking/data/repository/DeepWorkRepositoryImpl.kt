package com.jingom.deepworktracker.feature_tracking.data.repository

import com.jingom.deepworktracker.common.datetime.atFirstTimeOfDay
import com.jingom.deepworktracker.feature_tracking.data.local.DeepWorkLocalDataSource
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class DeepWorkRepositoryImpl @Inject constructor(
	private val deepWorkLocalDataSource: DeepWorkLocalDataSource
): DeepWorkRepository {

	override fun getDeepWorks(): Flow<List<DeepWork>> {
		return deepWorkLocalDataSource.getDeepWorks()
	}

	override fun getDeepWorksInLastYear(now: LocalDateTime): Flow<List<DeepWork>> {
		val todayOneYearsAgo = now.minusYears(1).atFirstTimeOfDay()
		val lastSundayOneYearsAgo = todayOneYearsAgo.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

		return deepWorkLocalDataSource.getDeepWorksInRange(lastSundayOneYearsAgo, now)
	}

	override suspend fun getDeepWorkById(id: Int): DeepWork? {
		return deepWorkLocalDataSource.getDeepWorkById(id)
	}

	override suspend fun insertDeepWork(deepWork: DeepWork): Int {
		return deepWorkLocalDataSource.insertDeepWork(deepWork)
	}

	override suspend fun deleteDeepWork(deepWork: DeepWork) {
		return deepWorkLocalDataSource.deleteDeepWork(deepWork)
	}
}