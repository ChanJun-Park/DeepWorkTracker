package com.jingom.deepworktracker.feature_tracking.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jingom.deepworktracker.feature_tracking.domain.usecase.DeepWorkUseCases
import com.jingom.deepworktracker.feature_tracking.presentation.dashboard.components.LastYearDeepWorkData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeepWorkYearRecordViewModel @Inject constructor(
	private val deepWorkUseCases: DeepWorkUseCases
): ViewModel() {

	private val _lastYearDeepWorkData = mutableStateOf(LastYearDeepWorkData())
	val lastYearDeepWorkData: State<LastYearDeepWorkData> = _lastYearDeepWorkData

	private var getDeepWorkTimesOnDayInLastYear: Job? = null

	init {
		getDeepWorkTimesOnDayInLastYear()
	}

	private fun getDeepWorkTimesOnDayInLastYear() {
		getDeepWorkTimesOnDayInLastYear?.cancel()
		getDeepWorkTimesOnDayInLastYear = deepWorkUseCases.getDeepWorkTimesOnDayInLastYear()
			.onEach { deepWorkTimesOnDayInLastYear ->
				_lastYearDeepWorkData.value = deepWorkTimesOnDayInLastYear
			}
			.launchIn(viewModelScope)
	}
}