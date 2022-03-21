package com.jingom.deepworktracker.feature_tracking.presentation

import androidx.lifecycle.ViewModel
import com.jingom.deepworktracker.feature_tracking.domain.usecase.DeepWorkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
	private val deepWorkUseCases: DeepWorkUseCases
): ViewModel() {

}