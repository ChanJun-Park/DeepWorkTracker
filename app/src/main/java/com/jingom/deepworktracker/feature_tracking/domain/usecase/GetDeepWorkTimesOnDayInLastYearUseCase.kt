package com.jingom.deepworktracker.feature_tracking.domain.usecase

import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetDeepWorkTimesOnDayInLastYearUseCase @Inject constructor(
	private val deepWorkRepository: DeepWorkRepository
) {
	operator fun invoke(): Flow<Map<LocalDate, DeepWorkTimesOnDay>> {
		val now = LocalDateTimes.now()

		return deepWorkRepository.getDeepWorksInLastYear(now).map { deepWorksInLastYear ->
			deepWorksInLastYear
				.groupingBy { it.startDateTime.toLocalDate() }
				.aggregate { key, accumulator: DeepWorkTimesOnDay?, element, first ->
					if (first) {
						DeepWorkTimesOnDay(key, element.duration)
					} else {
						accumulator?.copy(totalDeepWorkTime = accumulator.totalDeepWorkTime + element.duration)
							?: DeepWorkTimesOnDay(key, element.duration)
					}
				}
		}
	}
}