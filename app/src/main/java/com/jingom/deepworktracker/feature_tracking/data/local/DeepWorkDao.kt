package com.jingom.deepworktracker.feature_tracking.data.local

import androidx.room.*
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import kotlinx.coroutines.flow.Flow

@Dao
interface DeepWorkDao {
	@Query("SELECT * FROM deepwork")
	fun getDeepWorks(): Flow<List<DeepWork>>

	@Query("SELECT * FROM deepwork WHERE id = :id")
	suspend fun getDeepWorkById(id: Int): DeepWork?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertDeepWork(deepWork: DeepWork)

	@Delete
	suspend fun deleteDeepWork(deepWork: DeepWork)
}