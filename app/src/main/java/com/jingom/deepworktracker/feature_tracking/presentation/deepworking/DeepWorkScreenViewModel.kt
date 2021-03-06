package com.jingom.deepworktracker.feature_tracking.presentation.deepworking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jingom.deepworktracker.common.datetime.LocalDateTimes
import com.jingom.deepworktracker.common.utils.getLimitedLinesText
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkState
import com.jingom.deepworktracker.feature_tracking.domain.usecase.AddDeepWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask
import kotlin.math.absoluteValue

@HiltViewModel
class DeepWorkScreenViewModel @Inject constructor(
	private val addDeepWork: AddDeepWorkUseCase
) : ViewModel() {

	companion object {
		const val MAX_TITLE_LINES = 3
		const val MAX_TITLE_CHARACTERS = 100
	}

	private val _deepWork = mutableStateOf(DeepWork())
	val deepWork = _deepWork

	private val _deepWorkTitle = mutableStateOf("")
	val deepWorkTitle = _deepWorkTitle

	private val _deepWorkTime = mutableStateOf(0L)
	val deepWorkTime: State<Long> = _deepWorkTime

	private val _deepWorkState = mutableStateOf(DeepWorkState.INIT)
	val deepWorkState: State<DeepWorkState> = _deepWorkState

	private val timer = Timer()
	private var timerTask: TimerTask? = null

	private val _eventFlow = MutableSharedFlow<UiEvent>()
	val eventFlow = _eventFlow.asSharedFlow()

	fun startDeepWork() {
		deepWork.value = DeepWork()
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
	}

	private fun saveDeepWork() {
		viewModelScope.launch {
			val modifiedDeepWork = deepWork.value.copy(
				duration = deepWorkTime.value,
				lastWorkingDateTime = LocalDateTimes.now()
			)

			val id = addDeepWork(modifiedDeepWork)
			deepWork.value = modifiedDeepWork.copy(id = id)

			_eventFlow.emit(UiEvent.ShowAddDeepWorkAlert)
		}
	}

	private fun startTimer() {
		timerTask?.cancel()
		timerTask = timerTask {
			val now = LocalDateTimes.now()
			val startTime = _deepWork.value.startDateTime

			_deepWorkTime.value = ChronoUnit.SECONDS.between(now, startTime).absoluteValue
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

	fun onEvent(event: AddDeepWorkEvent) {
		when (event) {
			is AddDeepWorkEvent.EnteredTitle -> _deepWorkTitle.value = event.value.getLimitedLinesText(MAX_TITLE_LINES).take(MAX_TITLE_CHARACTERS)
			is AddDeepWorkEvent.SaveDeepWork -> saveDeepWork()
		}
	}

	fun saveTitle() {
		viewModelScope.launch {
			val modifiedDeepWork = deepWork.value.copy(title = deepWorkTitle.value)
			addDeepWork(modifiedDeepWork)

			_eventFlow.emit(UiEvent.DeepWorkSaved)
		}
	}

	sealed class UiEvent {
		object ShowAddDeepWorkAlert : UiEvent()
		object DeepWorkSaved : UiEvent()
	}
}