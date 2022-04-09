package com.jingom.deepworktracker.common.datetime

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

fun LocalDate.atStartDayOfWeek(): LocalDate = this.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))

fun LocalDate.isBeforeOrSame(targetDate: LocalDate): Boolean = this.isBefore(targetDate) || this.isEqual(targetDate)