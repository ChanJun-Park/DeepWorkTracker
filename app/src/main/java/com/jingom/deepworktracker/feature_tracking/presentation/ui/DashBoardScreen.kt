package com.jingom.deepworktracker.feature_tracking.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jingom.deepworktracker.common.ui.component.YearRecord
import com.jingom.deepworktracker.feature_tracking.presentation.DeepWorkYearRecordViewModel
import com.jingom.deepworktracker.feature_tracking.presentation.DeepWorksViewModel

@Composable
fun DashBoardScreen(
	navController: NavController,
	deepWorksViewModel: DeepWorksViewModel = hiltViewModel(),
	deepWorkYearRecordViewModel: DeepWorkYearRecordViewModel = hiltViewModel()
) {

	val scaffoldState = rememberScaffoldState()
	val scope = rememberCoroutineScope()

	Scaffold(
		scaffoldState = scaffoldState
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight()
		) {
			val lastYearDeepWorkData = deepWorkYearRecordViewModel.lastYearDeepWorkData.value
			YearRecord(lastYearDeepWorkData)
		}
	}
}