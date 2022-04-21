package com.jingom.deepworktracker.feature_tracking.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkState
import com.jingom.deepworktracker.feature_tracking.domain.usecase.AddDeepWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

@HiltViewModel
class DeepWorkScreenViewModel @Inject constructor(
	private val addDeepWorkUseCase: AddDeepWorkUseCase
) : ViewModel() {

	private var deepWork: DeepWork? = null

	private val _deepWorkTime = mutableStateOf(0L)
	val deepWorkTime: State<Long> = _deepWorkTime

	private val _deepWorkState = mutableStateOf(DeepWorkState.INIT)
	val deepWorkState: State<DeepWorkState> = _deepWorkState

	private val timer = Timer()
	private var timerTask: TimerTask? = null

	fun startDeepWork() {
		if (deepWork == null) {
			deepWork = DeepWork()
		}
		_deepWorkState.value = DeepWorkState.STARTED
		startTimer()
	}

	fun pauseDeepWork() {
		_deepWorkState.value = DeepWorkState.PAUSED
		stopTimer()
	}

	fun stopDeepWork() {
		stopTimer()
		_deepWorkState.value = DeepWorkState.STOPPED
		saveDeepWork()
		_deepWorkTime.value = 0
		deepWork = null
	}

	private fun saveDeepWork() {
		deepWork?.let {
			viewModelScope.launch {
				val modifiedDeepWork = it.copy(
					duration = _deepWorkTime.value,
					lastWorkingDateTime = LocalDateTimes.now()
				)
				addDeepWorkUseCase(modifiedDeepWork)
			}
		}
	}

	private fun startTimer() {
		timerTask?.cancel()
		timerTask = timerTask {
			_deepWorkTime.value++
		}
		timer.schedule(timerTask, 1000, 1000)
	}

	private fun stopTimer() {
		timerTask?.cancel()
	}

	override fun onCleared() {
		try {
			timer.cancel()
		} catch (e: Exception) {

		}
	}
}