package com.jingom.deepworktracker.feature_tracking.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.usecase.DeepWorkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeepWorksViewModel @Inject constructor(
	private val deepWorkUseCases: DeepWorkUseCases
) : ViewModel() {

	private val deepWorks = MutableLiveData<List<DeepWork>>()

	private var getDeepWorksJob: Job? = null

	init {
		getDeepWorks()
	}


	private fun getDeepWorks() {
		getDeepWorksJob?.cancel()
		getDeepWorksJob = deepWorkUseCases.getDeepWorksUseCase()
			.onEach { deepWorks ->
				this.deepWorks.value = deepWorks
			}
			.launchIn(viewModelScope)
	}
}