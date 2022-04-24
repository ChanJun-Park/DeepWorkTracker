package com.jingom.deepworktracker.feature_tracking.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jingom.deepworktracker.feature_tracking.presentation.dashboard.components.YearRecord
import com.jingom.deepworktracker.feature_tracking.presentation.util.Screen

@Composable
fun DashBoardScreen(
	navController: NavController,
	deepWorkYearRecordViewModel: DeepWorkYearRecordViewModel = hiltViewModel()
) {

	val scaffoldState = rememberScaffoldState()

	Scaffold(
		scaffoldState = scaffoldState,
		floatingActionButton = {
			Button(
				modifier = Modifier
					.width(100.dp)
					.height(100.dp),
				onClick = {
					navController.navigate(route = Screen.DeepWorkScreen.route)
				},
			) {
				Text("Start Deep Work")
			}
		}
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