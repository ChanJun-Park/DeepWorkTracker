package com.jingom.deepworktracker.common.database

import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import java.time.LocalDateTime

object SeedData {
	val deepWorks = listOf(
		DeepWork(
			id = 1,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 2, 21, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 2, 21, 9, 30, 0, 0)
		),
		DeepWork(
			id = 2,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 3, 1, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 3, 2, 9, 30, 0, 0)
		),
		DeepWork(
			id = 3,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 3, 9, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 3, 11, 10, 30, 0, 0)
		),
		DeepWork(
			id = 4,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 3, 10, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 3, 11, 9, 30, 0, 0)
		),
		DeepWork(
			id = 5,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 3, 19, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 3, 20, 9, 30, 0, 0)
		),
		DeepWork(
			id = 6,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 3, 19, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 3, 21, 8, 30, 0, 0)
		),
		DeepWork(
			id = 7,
			title = "Android DI",
			startDateTime = LocalDateTime.of(2022, 3, 20, 0, 0, 0, 0),
			duration = 3600L,
			pomodoroCount = 0,
			lastWorkingDateTime = LocalDateTime.of(2022, 3, 21, 9, 30, 0, 0)
		),
	)
}