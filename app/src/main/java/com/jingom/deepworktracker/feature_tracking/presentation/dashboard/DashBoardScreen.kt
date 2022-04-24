package com.jingom.deepworktracker.feature_tracking.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jingom.deepworktracker.R
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
			ExtendedFloatingActionButton(
				icon = { Icon(Icons.Filled.PlayArrow, contentDescription = stringResource(id = R.string.record_deep_work)) },
				text = { Text(stringResource(id = R.string.record_deep_work)) },
				onClick = { navController.navigate(route = Screen.DeepWorkScreen.route) }
			)
		},
		floatingActionButtonPosition = FabPosition.Center
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