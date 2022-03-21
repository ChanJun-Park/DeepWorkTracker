package com.jingom.deepworktracker.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.jingom.deepworktracker.common.datetime.DateTimeFormatters
import com.jingom.deepworktracker.feature_tracking.data.local.DeepWorkDao
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
	entities = [DeepWork::class],
	version = 1
)
@TypeConverters(Converters::class)
abstract class DeepWorkDatabase: RoomDatabase() {
	abstract val deepWorkDao: DeepWorkDao
}

class Converters {
	@TypeConverter
	fun fromStringToLocalDateTime(localDateTimeString: String): LocalDateTime? {
		return LocalDateTime.parse(localDateTimeString, DateTimeFormatters.LOCAL_DATE_TIME)
	}

	@TypeConverter
	fun fromLocalDateTimeToString(localDateTime: LocalDateTime): String {
		return localDateTime.format(DateTimeFormatters.LOCAL_DATE_TIME)
	}
}