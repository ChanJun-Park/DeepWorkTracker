package com.jingom.deepworktracker.feature_tracking.presentation.deepworking

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.ui.component.PositiveNegativeDialog
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkState
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.components.AddDeepWorkDialog
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.components.DeepWorkStateControlButton
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.components.DeepWorkStateControlButtonStyle
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun DeepWorkScreen(
	navController: NavController,
	viewModel: DeepWorkScreenViewModel = hiltViewModel()
) {

	// Remember a SystemUiController
	val systemUiController = rememberSystemUiController()

	SideEffect {
		// Update all of the system bar colors to be transparent, and use
		// dark icons if we're in light theme
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = false
		)
	}

	val deepWorkTimeState = viewModel.deepWorkTime.value
	val deepWorkState = viewModel.deepWorkState.value

	var stopDialogVisible by remember {
		mutableStateOf(false)
	}

	var addDeepWorkDialogVisible by remember {
		mutableStateOf(false)
	}

	LaunchedEffect(key1 = true) {
		viewModel.eventFlow.collectLatest { event ->
			when (event) {
				is DeepWorkScreenViewModel.UiEvent.ShowAddDeepWorkAlert -> {
					addDeepWorkDialogVisible = true
				}
				is DeepWorkScreenViewModel.UiEvent.DeepWorkSaved -> {
					navController.navigateUp()
				}
			}
		}
	}

	BoxWithConstraints(
		modifier = Modifier
			.fillMaxSize()
	) {
		Image(
			modifier = Modifier.fillMaxSize(),
			painter = painterResource(id = R.drawable.deep_work_screen_background),
			contentDescription = null,
			contentScale = ContentScale.Crop
		)
		Column(
			modifier = Modifier.fillMaxWidth()
		) {
			Canvas(
				modifier = Modifier
					.fillMaxSize()
					.weight(1f)
			) {
				drawContext.canvas.nativeCanvas.apply {
					drawText(
						deepWorkTimeState.toElapsedTimeText(),
						center.x,
						center.y,
						Paint().apply {
							isAntiAlias = true
							textSize = 40.sp.toPx()
							color = Color.White.toArgb()
							textAlign = Paint.Align.CENTER
						}
					)
				}
			}
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.height(200.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center
			) {
				when (deepWorkState) {
					DeepWorkState.INIT, DeepWorkState.STOPPED -> {
						DeepWorkStateControlButton(
							text = stringResource(id = R.string.start_deep_work),
							style = DeepWorkStateControlButtonStyle(
								textColor = Color.Black,
								iconImageVector = Icons.Rounded.PlayArrow,
								backgroundColor = Color.White,
								borderColor = Color.White
							),
							onClick = viewModel::startDeepWork
						)
					}
					DeepWorkState.STARTED -> {
						DeepWorkStateControlButton(
							text = stringResource(id = R.string.pause_deep_work),
							style = DeepWorkStateControlButtonStyle(
								textColor = Color.White,
								iconImageVector = Icons.Rounded.Pause,
								backgroundColor = Color.Transparent,
								borderColor = Color.White
							),
							onClick = viewModel::pauseDeepWork
						)
					}
					DeepWorkState.PAUSED -> {
						DeepWorkStateControlButton(
							text = stringResource(id = R.string.resume_deep_work),
							style = DeepWorkStateControlButtonStyle(
								textColor = Color.Black,
								iconImageVector = Icons.Rounded.PlayArrow,
								backgroundColor = Color.White,
								borderColor = Color.White
							),
							onClick = viewModel::startDeepWork
						)
						DeepWorkStateControlButton(
							text = stringResource(id = R.string.stop_deep_work),
							style = DeepWorkStateControlButtonStyle(
								textColor = Color.White,
								iconImageVector = Icons.Rounded.Stop,
								backgroundColor = Color.Transparent,
								borderColor = Color.White
							),
							onClick = { stopDialogVisible = true }
						)
					}
				}
			}
		}

		AnimatedVisibility(
			visible = stopDialogVisible,
			enter = fadeIn(),
			exit = ExitTransition.None
		) {
			PositiveNegativeDialog(
				title = stringResource(id = R.string.quit_deep_work_alert_title),
				onPositiveButtonClicked = {
					viewModel.stopDeepWork()
					stopDialogVisible = false
				},
				onNegativeButtonClicked = {
					stopDialogVisible = false
				}
			)
		}

		AnimatedVisibility(
			visible = addDeepWorkDialogVisible,
			enter = fadeIn(),
			exit = ExitTransition.None
		) {
			AddDeepWorkDialog()
		}
	}
}

fun Long.toElapsedTimeText(): String {
	var time = this
	val hour = time / ChronoUnit.HOURS.duration.seconds

	time %= ChronoUnit.HOURS.duration.seconds
	val minute = time / ChronoUnit.MINUTES.duration.seconds

	time %= ChronoUnit.MINUTES.duration.seconds
	val second = time

	return String.format("%02d:%02d:%02d", hour, minute, second)
}
