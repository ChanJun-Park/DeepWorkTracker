package com.jingom.deepworktracker.feature_tracking.presentation.deepworking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DeepWorkStateControlButtonStyle(
	val textColor: Color = Color.Black,
	val fontSize: TextUnit = 16.sp,
	val iconImageVector: ImageVector = Icons.Rounded.PlayArrow,
	val backgroundColor: Color = Color.White,
	val borderColor: Color = Color.White,
)

@Composable
fun DeepWorkStateControlButton(
	modifier: Modifier = Modifier,
	style: DeepWorkStateControlButtonStyle = DeepWorkStateControlButtonStyle(),
	text: String = "",
	onClick: () -> Unit = {}
) {
	Row(
		modifier = modifier
			.background(
				color = style.backgroundColor,
				shape = RoundedCornerShape(percent = 100)
			)
			.border(
				width = 1.dp,
				color = style.borderColor,
				shape = RoundedCornerShape(percent = 100)
			)
			.clickable { onClick() }
			.padding(20.dp),
	) {
		Icon(
			imageVector = style.iconImageVector,
			contentDescription = text,
			tint = style.textColor
		)
		Text(
			text = text,
			fontSize = style.fontSize,
			color = style.textColor
		)
	}
}