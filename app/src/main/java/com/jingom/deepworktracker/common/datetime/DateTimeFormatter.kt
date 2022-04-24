package com.jingom.deepworktracker.common.datetime

import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.ResolverStyle
import java.time.temporal.ChronoField

object DateTimeFormatters {
	/**
	 * yyyy-MM-dd
	 */
	@JvmField
	val ISO_LOCAL_DATE: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

	/**
	 * HH:mm:ss
	 */
	@JvmField
	val LOCAL_TIME: DateTimeFormatter = DateTimeFormatterBuilder()
		.appendValue(ChronoField.HOUR_OF_DAY, 2)
		.appendLiteral(':')
		.appendValue(ChronoField.MINUTE_OF_HOUR, 2)
		.optionalStart()
		.appendLiteral(':')
		.appendValue(ChronoField.SECOND_OF_MINUTE, 2)
		.toFormatter()
		.withResolverStyle(ResolverStyle.STRICT)

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	@JvmField
	val LOCAL_DATE_TIME: DateTimeFormatter = DateTimeFormatterBuilder()
		.parseCaseInsensitive()
		.append(ISO_LOCAL_DATE)
		.appendLiteral(" ")
		.append(LOCAL_TIME)
		.toFormatter()
		.withResolverStyle(ResolverStyle.STRICT)
		.withChronology(IsoChronology.INSTANCE)
}