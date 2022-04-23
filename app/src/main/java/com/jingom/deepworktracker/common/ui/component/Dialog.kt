package com.jingom.deepworktracker.common.ui.component

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
import com.jingom.deepworktracker.R

@Composable
fun PositiveNegativeDialog(
	title: String,
	onPositiveButtonClicked: () -> Unit = {},
	onNegativeButtonClicked: () -> Unit = {}
) {
	Dialog(onDismissRequest = onNegativeButtonClicked) {
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
						.fillMaxSize()
				)
				Text(
					text = title,
					textAlign = TextAlign.Center,
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight()
						.padding(vertical = 8.dp),
					fontSize = 16.sp
				)
				Spacer(
					modifier = Modifier
						.height(12.dp)
						.fillMaxSize()
				)
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight(),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Button(
						onClick = onPositiveButtonClicked,
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
						Text(stringResource(id = R.string.yes))
					}
					Button(
						onClick = onNegativeButtonClicked,
						modifier = Modifier
							.wrapContentWidth()
							.padding(horizontal = 20.dp),
						shape = RoundedCornerShape(24.dp),
						colors = ButtonDefaults.textButtonColors(
							backgroundColor = Color.Transparent,
							contentColor = Color.White
						),
						border = BorderStroke(
							width = 1.dp,
							color = Color.White
						)
					) {
						Text(stringResource(id = R.string.no))
					}
				}
				Spacer(
					modifier = Modifier
						.height(12.dp)
						.fillMaxSize()
				)
			}
		}
	}
}

@Preview(widthDp = 570, heightDp = 1024)
@Composable
fun PositiveNegativeDialogPreview() {
	PositiveNegativeDialog(
		title = stringResource(id = R.string.quit_deep_work_alert_title),
	)
}