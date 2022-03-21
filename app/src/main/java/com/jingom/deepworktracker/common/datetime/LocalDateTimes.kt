package com.jingom.deepworktracker.common.datetime

import java.time.LocalDateTime

object LocalDateTimes {
	fun now() = LocalDateTime.now(AppClock.getClock())
}

fun LocalDateTime.atFirstTimeOfDay(): LocalDateTime {
	return this.withHour(0).withMinute(0).withSecond(0).withNano(0)
}