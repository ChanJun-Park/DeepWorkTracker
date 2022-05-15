package com.jingom.deepworktracker.feature_tracking.presentation.dashboard

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.feature_tracking.presentation.dashboard.components.YearRecord
import com.jingom.deepworktracker.feature_tracking.presentation.util.Screen

@Composable
fun DashBoardScreen(
	navController: NavController,
	deepWorkYearRecordViewModel: DeepWorkYearRecordViewModel = hiltViewModel()
) {

	// Remember a SystemUiController
	val systemUiController = rememberSystemUiController()
	val useDarkIcons = MaterialTheme.colors.isLight

	SideEffect {
		// Update all of the system bar colors to be transparent, and use
		// dark icons if we're in light theme
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = useDarkIcons
		)
	}

	val scaffoldState = rememberScaffoldState()

	Scaffold(
		modifier = Modifier
			.padding(WindowInsets.statusBars.asPaddingValues())
			.padding(WindowInsets.navigationBars.asPaddingValues()),
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
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
			val verticalScrollState = rememberScrollState()
			Column(
				modifier = Modifier
					.fillMaxSize()
					.verticalScroll(
						state = verticalScrollState,
						flingBehavior = ScrollableDefaults.flingBehavior()
					)
			) {
				Column(
					modifier = Modifier
						.wrapContentHeight()
						.fillMaxWidth()
						.padding(10.dp)
				) {
					Column(
						modifier = Modifier
							.wrapContentHeight()
							.fillMaxWidth()
							.padding(horizontal = 10.dp)
					) {
						Text(
							text = stringResource(id = R.string.year_record),
							style = MaterialTheme.typography.h5
						)

						val totalDeepWorkHourInLastYear = deepWorkYearRecordViewModel.lastYearDeepWorkData.value.getTotalDeepWorkHour()
						Text(
							text = stringResource(id = R.string.year_record_hour, totalDeepWorkHourInLastYear),
							style = MaterialTheme.typography.h6
						)
					}

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
		}
	}
}