package com.jingom.deepworktracker.common.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.datetime.atStartDayOfWeek
import com.jingom.deepworktracker.common.datetime.isBeforeOrSame
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import com.jingom.deepworktracker.feature_tracking.presentation.LastYearDeepWorkData

data class DayRecordColor(
	val blockColor: Color,
	val blockContainerColor: Color
)

@Composable
fun YearRecord(
	lastYearDeepWorkData: LastYearDeepWorkData
) {
	BoxWithConstraints(
		modifier = Modifier.fillMaxSize()
	) {
		val padding = 10.dp
		val gapBetweenBlocks = 5.dp
		val recordBlockSize = 10.dp
		val recordBlockRadius = 2.dp
		val containerWidth = 810.dp
		val containerHeight = 120.dp
		val containerColor = colorResource(id = R.color.deepwork_year_record_container)
		var topLeft by remember {
			mutableStateOf(Offset(0f, 0f))
		}

		val recordMap = lastYearDeepWorkData.deepWorkRecordMap
		val baseDate = lastYearDeepWorkData.baseDate
		var indexDate = baseDate.atStartDayOfWeek().minusWeeks(52)
		val drawTargetColorList = mutableListOf<DayRecordColor>()

		while (indexDate.isBeforeOrSame(baseDate)) {
			val deepWorkLevel = recordMap[indexDate]?.getDeepWorkLevel() ?: DeepWorkTimesOnDay.DeepWorkLevel.LEVEL0

			val blockColor = colorResource(id = deepWorkLevel.colorId)
			val blockContainerColor = colorResource(id = deepWorkLevel.containerColorId)

			drawTargetColorList.add(DayRecordColor(blockColor, blockContainerColor))

			indexDate = indexDate.plusDays(1)
		}

		fun DrawScope.getRecordOffset(index: Int): Offset {
			val row = index % 7
			val col = index / 7

			val x = topLeft.x + padding.toPx() + col * gapBetweenBlocks.toPx() + col * recordBlockSize.toPx()
			val y = topLeft.y + padding.toPx() + row * gapBetweenBlocks.toPx() + row * recordBlockSize.toPx()

			return Offset(x, y)
		}

		fun DrawScope.drawSingleRecord(offset: Offset, dayRecordColor: DayRecordColor) {
			drawRoundRect(
				color = dayRecordColor.blockColor,
				topLeft = offset,
				size = Size(recordBlockSize.toPx(), recordBlockSize.toPx()),
				cornerRadius = CornerRadius(recordBlockRadius.toPx(), recordBlockRadius.toPx())
			)

			drawRoundRect(
				color = dayRecordColor.blockContainerColor,
				topLeft = offset,
				size = Size(recordBlockSize.toPx(), recordBlockSize.toPx()),
				cornerRadius = CornerRadius(recordBlockRadius.toPx(), recordBlockRadius.toPx()),
				style = Stroke()
			)
		}


		Canvas(
			modifier = Modifier
				.padding(10.dp)
				.fillMaxSize()
				.pointerInput(key1 = true) {
					val minX = -(containerWidth.toPx() - size.width + 10.dp.toPx())
					val maxX = 10.dp.toPx()

					detectHorizontalDragGestures { _, dragAmount ->
						val newX = (topLeft.x + dragAmount).coerceIn(minX, maxX)
						topLeft = Offset(newX, topLeft.y)
					}
				}
		) {
			drawRoundRect(
				color = containerColor,
				topLeft = topLeft,
				size = Size(containerWidth.toPx(), containerHeight.toPx()),
				cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
				style = Stroke(width = 2.dp.toPx())
			)

			drawTargetColorList.forEachIndexed { index, yearRecordColor ->
				val offset = getRecordOffset(index)

				drawSingleRecord(offset, yearRecordColor)
			}
		}
	}
}

