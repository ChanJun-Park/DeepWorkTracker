package com.jingom.deepworktracker.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.jingom.deepworktracker.common.database.DeepWorkDatabase
import com.jingom.deepworktracker.feature_tracking.data.local.DeepWorkDao
import com.jingom.deepworktracker.feature_tracking.data.local.DeepWorkLocalDataSource
import com.jingom.deepworktracker.feature_tracking.data.local.DeepWorkLocalDataSourceImpl
import com.jingom.deepworktracker.feature_tracking.data.repository.DeepWorkRepositoryImpl
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

	@Binds
	@Singleton
	abstract fun deepWorkLocalDataSource(deepWorkLocalDataSourceImpl: DeepWorkLocalDataSourceImpl): DeepWorkLocalDataSource

	@Binds
	@Singleton
	abstract fun deepWorkRepository(deepWorkRepository: DeepWorkRepositoryImpl): DeepWorkRepository

	companion object {
		@Provides
		@Singleton
		fun deepWorkDatabase(application: Application): DeepWorkDatabase {
			return Room.databaseBuilder(
				application,
				DeepWorkDatabase::class.java,
				"deep_work_db"
			)
				.fallbackToDestructiveMigration()
				.build()
		}

		@Provides
		@Singleton
		fun deepWorkDao(deepWorkDatabase: DeepWorkDatabase): DeepWorkDao = deepWorkDatabase.deepWorkDao
	}
}