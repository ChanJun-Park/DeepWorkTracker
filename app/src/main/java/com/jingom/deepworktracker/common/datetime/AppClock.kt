package com.jingom.deepworktracker.common.datetime

import androidx.annotation.VisibleForTesting
import java.time.Clock
import java.time.ZoneId
import java.util.concurrent.atomic.AtomicReference

object AppClock {
	private val atomicClock = AtomicReference<Clock>()

	@JvmStatic
	internal fun refreshAppClock() {
		atomicClock.set(
			Clock.systemDefaultZone()
		)
	}

	@JvmStatic
	fun getClock(): Clock {
		if (atomicClock.get() == null) {
			refreshAppClock()
		}
		return atomicClock.get()
	}

	@VisibleForTesting
	fun setClock(clock: Clock) {
		atomicClock.set(clock)
	}

	@VisibleForTesting
	fun setZoneId(zoneId: String) {
		setClock(Clock.system(ZoneId.of(zoneId)))
	}
}