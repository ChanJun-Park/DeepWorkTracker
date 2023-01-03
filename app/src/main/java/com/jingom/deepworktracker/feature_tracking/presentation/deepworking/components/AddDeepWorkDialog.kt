package com.jingom.deepworktracker.feature_tracking.presentation.deepworking.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.datetime.format
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.AddDeepWorkEvent
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.DeepWorkScreenViewModel
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddDeepWorkDialog(deepWorkScreenViewModel: DeepWorkScreenViewModel = hiltViewModel()) {
	val title = deepWorkScreenViewModel.deepWorkTitle.value
	val savedDeepWork = deepWorkScreenViewModel.deepWork.value

	Dialog(
		onDismissRequest = {},
		properties = DialogProperties(
			dismissOnBackPress = false,
			dismissOnClickOutside = false,
			usePlatformDefaultWidth = false
		)
	) {
		Surface(
			modifier = Modifier
				.padding(10.dp)
				.wrapContentSize(),
			shape = RoundedCornerShape(12.dp)
		) {
			Column(
				modifier = Modifier
					.wrapContentSize(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Spacer(
					modifier = Modifier
						.height(12.dp)
						.fillMaxWidth()
				)
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentWidth()
						.padding(horizontal = 10.dp)
				) {
					OutlinedTextField(
						modifier = Modifier
							.fillMaxWidth()
							.wrapContentHeight()
							.padding(vertical = 8.dp),
						value = title,
						onValueChange = {
							deepWorkScreenViewModel.onEvent(AddDeepWorkEvent.EnteredTitle(it))
						},
						label = {
							Text(text = stringResource(id = R.string.deep_work_title))
						},
						maxLines = 3,
					)
				}

				Row(
					modifier = Modifier.wrapContentSize()
				) {
					Text(text = "${stringResource(id = R.string.start_date_time)}: ${savedDeepWork.startDateTime.format()}")
				}

				Row(
					modifier = Modifier.wrapContentSize()
				) {
					Text(text = "${stringResource(id = R.string.end_date_time)}: ${savedDeepWork.startDateTime.plusSeconds(savedDeepWork.duration).format()}")
				}

				Row(
					modifier = Modifier.wrapContentSize()
				) {
					Text(text = "${stringResource(id = R.string.total_deep_work_time)}: ${savedDeepWork.duration.toHMSFormatText()}")
				}

				Spacer(
					modifier = Modifier
						.height(12.dp)
						.fillMaxWidth()
				)

				Button(
					onClick = deepWorkScreenViewModel::saveTitle,
					modifier = Modifier
						.wrapContentWidth()
						.padding(horizontal = 20.dp),
					shape = RoundedCornerShape(24.dp),
					border = BorderStroke(
						width = 1.dp,
						color = Color.White
					)
				) {
					Text(stringResource(id = R.string.save))
				}

				Spacer(
					modifier = Modifier
						.height(12.dp)
						.fillMaxWidth()
				)
			}
		}
	}
}

fun Long.toHMSFormatText(): String {
	var formattedString = ""
	var time = this
	val hour = time / ChronoUnit.HOURS.duration.seconds

	if (hour != 0L) {
		formattedString += "${hour}h "
	}

	time %= ChronoUnit.HOURS.duration.seconds
	val minute = time / ChronoUnit.MINUTES.duration.seconds

	if (minute != 0L) {
		formattedString += "${minute}m "
	}

	time %= ChronoUnit.MINUTES.duration.seconds
	val second = time

	if (second != 0L) {
		formattedString += "${second}s"
	}

	return formattedString
}
