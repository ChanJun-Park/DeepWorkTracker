package com.jingom.deepworktracker.feature_tracking.data.local

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeepWorkLocalDataSourceImpl @Inject constructor(
	private val deepWorkDao: DeepWorkDao
): DeepWorkLocalDataSource {
	override fun getDeepWorks(): Flow<List<DeepWork>> {
		return deepWorkDao.getDeepWorks()
	}

	override suspend fun getDeepWorkById(id: Int): DeepWork? {
		return deepWorkDao.getDeepWorkById(id)
	}

	override suspend fun insertDeepWork(deepWork: DeepWork) {
		return deepWorkDao.insertDeepWork(deepWork)
	}

	override suspend fun deleteDeepWork(deepWork: DeepWork) {
		return deepWorkDao.deleteDeepWork(deepWork)
	}
}