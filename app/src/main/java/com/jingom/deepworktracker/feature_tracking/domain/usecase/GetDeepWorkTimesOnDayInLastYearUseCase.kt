package com.jingom.deepworktracker.feature_tracking.domain.usecase

import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import com.jingom.deepworktracker.feature_tracking.domain.repository.DeepWorkRepository
import com.jingom.deepworktracker.feature_tracking.presentation.dashboard.components.LastYearDeepWorkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetDeepWorkTimesOnDayInLastYearUseCase @Inject constructor(
	private val deepWorkRepository: DeepWorkRepository
) {
	operator fun invoke(): Flow<LastYearDeepWorkData> {
		val now = LocalDateTimes.now()

		return deepWorkRepository.getDeepWorksInLastYear(now).map { deepWorksInLastYear ->
			val map = deepWorksInLastYear
				.groupingBy { it.startDateTime.toLocalDate() }
				.aggregate { key: LocalDate, accumulator: DeepWorkTimesOnDay?, element: DeepWork, first: Boolean ->
					if (first) {
						DeepWorkTimesOnDay(key, element.duration)
					} else {
						accumulator?.copy(totalDeepWorkTime = accumulator.totalDeepWorkTime + element.duration)
							?: DeepWorkTimesOnDay(key, element.duration)
					}
				}

			LastYearDeepWorkData(
				baseDate = now.toLocalDate(),
				deepWorkRecordMap = map
			)
		}
	}
}