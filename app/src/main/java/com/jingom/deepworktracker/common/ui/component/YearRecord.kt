package com.jingom.deepworktracker.common.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.datetime.atStartDayOfWeek
import com.jingom.deepworktracker.common.datetime.isBeforeOrSame
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import com.jingom.deepworktracker.feature_tracking.presentation.LastYearDeepWorkData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val WEEK_NUMBERS_OF_YEAR = 53
private const val DAY_NUMBERS_OF_WEEK = 7

data class DayRecordColor(
	val blockColor: Color,
	val blockContainerColor: Color
)

data class YearRecordStyle(
	val gapBetweenBlocks: Dp = 5.dp,
	val recordBlockSize: Dp = 10.dp,
	val recordBlockRadius: Dp = 2.dp,
	val containerStrokeWidth: Dp = 2.dp,
	val containerRadius: Dp = 2.dp,
	val containerWidth: Dp = 810.dp,
	val containerHeight: Dp = 120.dp,
)

@Composable
fun YearRecord(
	lastYearDeepWorkData: LastYearDeepWorkData,
	yearRecordStyle: YearRecordStyle = YearRecordStyle()
) {
	val scrollState = rememberScrollState()
	BoxWithConstraints(
		modifier = Modifier
			.fillMaxSize()
			.horizontalScroll(scrollState)
	) {
		val padding = 10.dp
		val containerWidth = padding * 2 + yearRecordStyle.recordBlockSize * WEEK_NUMBERS_OF_YEAR + yearRecordStyle.gapBetweenBlocks * (WEEK_NUMBERS_OF_YEAR - 1)
		val containerHeight = padding * 2 + yearRecordStyle.recordBlockSize * DAY_NUMBERS_OF_WEEK + yearRecordStyle.gapBetweenBlocks * (DAY_NUMBERS_OF_WEEK - 1)
		val containerColor = colorResource(id = R.color.deepwork_year_record_container)

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

			val x = padding.toPx() + col * yearRecordStyle.gapBetweenBlocks.toPx() + col * yearRecordStyle.recordBlockSize.toPx()
			val y = padding.toPx() + row * yearRecordStyle.gapBetweenBlocks.toPx() + row * yearRecordStyle.recordBlockSize.toPx()

			return Offset(x, y)
		}

		fun DrawScope.drawSingleRecord(offset: Offset, dayRecordColor: DayRecordColor) {
			drawRoundRect(
				color = dayRecordColor.blockColor,
				topLeft = offset,
				size = Size(yearRecordStyle.recordBlockSize.toPx(), yearRecordStyle.recordBlockSize.toPx()),
				cornerRadius = CornerRadius(yearRecordStyle.recordBlockRadius.toPx(), yearRecordStyle.recordBlockRadius.toPx())
			)

			drawRoundRect(
				color = dayRecordColor.blockContainerColor,
				topLeft = offset,
				size = Size(yearRecordStyle.recordBlockSize.toPx(), yearRecordStyle.recordBlockSize.toPx()),
				cornerRadius = CornerRadius(yearRecordStyle.recordBlockRadius.toPx(), yearRecordStyle.recordBlockRadius.toPx()),
				style = Stroke()
			)
		}

		Canvas(
			modifier = Modifier
				.padding(10.dp)
				.fillMaxHeight()
				.width(containerWidth)
				.pointerInput(Unit) {
					coroutineScope {
						launch {
							val offsetXLowerBound = containerWidth.toPx() + 10.dp.toPx()
							scrollState.scrollTo(value = offsetXLowerBound.roundToInt())
						}
					}
				}
		) {
			drawRoundRect(
				color = containerColor,
				size = Size(containerWidth.toPx(), containerHeight.toPx()),
				cornerRadius = CornerRadius(yearRecordStyle.containerRadius.toPx(), yearRecordStyle.containerRadius.toPx()),
				style = Stroke(width = yearRecordStyle.containerStrokeWidth.toPx())
			)

			drawTargetColorList.forEachIndexed { index, yearRecordColor ->
				val offset = getRecordOffset(index)

				drawSingleRecord(offset, yearRecordColor)
			}
		}
	}
}

