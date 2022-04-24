package com.jingom.deepworktracker.feature_tracking.presentation.deepworking.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.AddDeepWorkEvent
import com.jingom.deepworktracker.feature_tracking.presentation.deepworking.DeepWorkScreenViewModel

@Composable
fun AddDeepWorkDialog(deepWorkScreenViewModel: DeepWorkScreenViewModel = hiltViewModel()) {
	val title = deepWorkScreenViewModel.deepWorkTitle.value

	Dialog(
		onDismissRequest = {},
		properties = DialogProperties(
			dismissOnBackPress = false,
			dismissOnClickOutside = false,
		)
	) {
		Surface(
			modifier = Modifier
				.wrapContentSize()
				.wrapContentHeight(),
			shape = RoundedCornerShape(12.dp),
			color = Color.White
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Spacer(
					modifier = Modifier
						.height(12.dp)
						.fillMaxWidth()
				)
				OutlinedTextField(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight()
						.padding(vertical = 8.dp),
					value = title,
					onValueChange = {
						deepWorkScreenViewModel.onEvent(AddDeepWorkEvent.EnteredTitle(it))
					},
				)

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
					colors = ButtonDefaults.textButtonColors(
						backgroundColor = Color.White,
						contentColor = Color.Black
					),
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

@Preview
@Composable
fun DeepWorkSaveDialogPreview() {
	AddDeepWorkDialog()
}