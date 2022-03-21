package com.jingom.deepworktracker.dependencyinjection

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jingom.deepworktracker.common.database.DeepWorkDatabase
import com.jingom.deepworktracker.common.database.SeedData
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
		private lateinit var appDatabase: DeepWorkDatabase

		@Provides
		@Singleton
		fun deepWorkDatabase(application: Application): DeepWorkDatabase {
			appDatabase = Room.databaseBuilder(
				application,
				DeepWorkDatabase::class.java,
				"deep_work_db"
			)
				.fallbackToDestructiveMigration()
				.addCallback(object : RoomDatabase.Callback() {
					override fun onCreate(db: SupportSQLiteDatabase) {
						super.onCreate(db)
						GlobalScope.launch(Dispatchers.IO) {
							insertSeedData()
						}
					}
				})
				.build()

			return appDatabase
		}

		suspend fun insertSeedData() {
			with(appDatabase) {
				withTransaction {
					with(deepWorkDao) {
						insertDeepWorks(SeedData.deepWorks)
					}
				}
			}
		}

		@Provides
		@Singleton
		fun deepWorkDao(deepWorkDatabase: DeepWorkDatabase): DeepWorkDao = deepWorkDatabase.deepWorkDao
	}
}