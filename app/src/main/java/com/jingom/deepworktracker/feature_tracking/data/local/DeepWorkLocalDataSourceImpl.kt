package com.jingom.deepworktracker.feature_tracking.data.local

import com.jingom.deepworktracker.dependencyinjection.app.IoDispatcher
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class DeepWorkLocalDataSourceImpl @Inject constructor(
	private val deepWorkDao: DeepWorkDao,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DeepWorkLocalDataSource {
	override fun getDeepWorks(): Flow<List<DeepWork>> {
		return deepWorkDao.getDeepWorks()
	}

	override fun getDeepWorksInRange(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Flow<List<DeepWork>> {
		return deepWorkDao.getDeepWorksInRange(startDateTime, endDateTime)
	}

	override suspend fun getDeepWorkById(id: Int): DeepWork? {
		return withContext(ioDispatcher) {
			deepWorkDao.getDeepWorkById(id)
		}
	}

	override suspend fun insertDeepWork(deepWork: DeepWork): Int {
		return withContext(ioDispatcher) {
			deepWorkDao.insertDeepWork(deepWork)
		}
	}

	override suspend fun deleteDeepWork(deepWork: DeepWork) {
		return withContext(ioDispatcher) {
			deepWorkDao.deleteDeepWork(deepWork)
		}
	}
}