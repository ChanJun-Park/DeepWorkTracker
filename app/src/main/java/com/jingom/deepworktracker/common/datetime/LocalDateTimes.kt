package com.jingom.deepworktracker.common.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimes {
	fun now(): LocalDateTime = LocalDateTime.now(AppClock.getClock())
}

fun LocalDateTime.atFirstTimeOfDay(): LocalDateTime {
	return this.withHour(0).withMinute(0).withSecond(0).withNano(0)
}

fun LocalDateTime.atLastTimeOfDay(): LocalDateTime {
	return this.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999)
}

fun LocalDateTime.format(formatter: DateTimeFormatter = DateTimeFormatters.LOCAL_DATE_TIME): String = this.format(formatter)
