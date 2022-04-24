package com.jingom.deepworktracker.feature_tracking.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import java.lang.Exception
import java.time.LocalDateTime

@Entity
data class DeepWork(
	@PrimaryKey
	val id: Long? = null,
	val title: String = "",
	val startDateTime: LocalDateTime = LocalDateTimes.now(),
	val duration: Long = 0,
	val pomodoroCount: Int = 0,
	val lastWorkingDateTime: LocalDateTime = LocalDateTimes.now()
)

class InvalidDeepWorkException(message: String): Exception(message)