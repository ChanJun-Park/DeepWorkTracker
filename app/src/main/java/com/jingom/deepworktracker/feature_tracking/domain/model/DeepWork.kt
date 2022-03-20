package com.jingom.deepworktracker.feature_tracking.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception
import java.time.LocalDateTime

@Entity
data class DeepWork(
	@PrimaryKey
	val id: Int? = null,
	val title: String,
	val startDateTime: LocalDateTime,
	val duration: Long,
	val pomodoroCount: Int,
	val lastWorkingDateTime: LocalDateTime
)

class InvalidDeepWorkException(message: String): Exception(message)