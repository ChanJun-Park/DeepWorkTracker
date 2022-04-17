package com.jingom.deepworktracker.feature_tracking.presentation.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkState
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun DeepWorkScreen(
	navController: NavController,
	title: String = "",
	startDateTime: LocalDateTime? = null,
	durationInSecond: Long = 3890,
	deepWorkState: DeepWorkState = DeepWorkState.INIT
) {
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
						durationInSecond.toElapsedTimeText(),
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
				Row(
					modifier = Modifier
						.background(
							color = Color.White,
							shape = RoundedCornerShape(percent = 100)
						)
						.padding(20.dp),
				) {
					Icon(
						imageVector = Icons.Rounded.PlayArrow,
						contentDescription = stringResource(R.string.start_deep_work)
					)
					Text(
						text = stringResource(R.string.start_deep_work),
						fontSize = 16.sp
					)
				}
			}
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