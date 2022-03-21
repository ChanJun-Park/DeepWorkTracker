package com.jingom.deepworktracker.feature_tracking.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jingom.deepworktracker.feature_tracking.domain.usecase.DeepWorkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
	private val deepWorkUseCases: DeepWorkUseCases
): ViewModel() {

	private val _dashBoardState = MutableLiveData(DashBoardState())
	val dashBoardState: LiveData<DashBoardState> = _dashBoardState

	private var getDeepWorksJob: Job? = null
	private var getDeepWorkTimesOnDayInLastYear: Job? = null

	init {
		getDeepWorks()

		getDeepWorkTimesOnDayInLastYear()
	}

	private fun getDeepWorks() {
		getDeepWorksJob?.cancel()
		getDeepWorksJob = deepWorkUseCases.getDeepWorksUseCase()
			.onEach { deepWorks ->
				_dashBoardState.value = dashBoardState.value?.copy(deepWorks = deepWorks)
			}
			.launchIn(viewModelScope)
	}

	private fun getDeepWorkTimesOnDayInLastYear() {
		getDeepWorkTimesOnDayInLastYear?.cancel()
		getDeepWorkTimesOnDayInLastYear = deepWorkUseCases.getDeepWorkTimesOnDayInLastYearUseCase()
			.onEach { deepWorkTimesOnDayInLastYear ->
				_dashBoardState.value = dashBoardState.value?.copy(deepWorkTimesOnDayInLastYear = deepWorkTimesOnDayInLastYear)
			}
			.launchIn(viewModelScope)
	}
}