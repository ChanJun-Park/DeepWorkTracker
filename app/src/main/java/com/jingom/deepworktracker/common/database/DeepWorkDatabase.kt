package com.jingom.deepworktracker.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jingom.deepworktracker.feature_tracking.data.local.DeepWorkDao
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork

@Database(
	entities = [DeepWork::class],
	version = 1
)
abstract class DeepWorkDatabase: RoomDatabase() {
	abstract val deepWorkDao: DeepWorkDao
}