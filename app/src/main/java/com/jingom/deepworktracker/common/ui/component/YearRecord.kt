package com.jingom.deepworktracker.common.ui.component

import android.graphics.Rect
import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.datetime.atStartDayOfWeek
import com.jingom.deepworktracker.common.datetime.isBeforeOrSame
import com.jingom.deepworktracker.common.datetime.isFirstDayOfMonth
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import com.jingom.deepworktracker.feature_tracking.presentation.LastYearDeepWorkData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

private const val WEEK_NUMBERS_OF_YEAR = 53
private const val DAY_NUMBERS_OF_WEEK = 7

data class DayRecordData(
	val blockColor: Color,
	val blockContainerColor: Color,
	val date: LocalDate
)

data class YearRecordStyle(
	val gapBetweenBlocks: Dp = 5.dp,
	val recordBlockSize: Dp = 12.dp,
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
	BoxWithConstraints(
		modifier = Modifier.fillMaxSize()
	) {
		val paddingTop = 25.dp
		val paddingBottom = 30.dp
		val paddingStart = 10.dp
		val paddingEnd = 25.dp
		val yearRecordWidth = paddingStart + paddingEnd + yearRecordStyle.recordBlockSize * WEEK_NUMBERS_OF_YEAR + yearRecordStyle.gapBetweenBlocks * (WEEK_NUMBERS_OF_YEAR - 1)
		val initialYearRecordHeight = paddingTop + paddingBottom + yearRecordStyle.recordBlockSize * DAY_NUMBERS_OF_WEEK + yearRecordStyle.gapBetweenBlocks * (DAY_NUMBERS_OF_WEEK - 1)
		var yearRecordHeight by remember {
			mutableStateOf(initialYearRecordHeight)
		}

		val containerColor = colorResource(id = R.color.deepwork_year_record_container)
		val yearRecordTextColor = colorResource(id = R.color.year_record_text)
		val yearRecordOffsetX = remember { Animatable(0f) }

		val recordMap = lastYearDeepWorkData.deepWorkRecordMap
		val baseDate = lastYearDeepWorkData.baseDate
		var indexDate = baseDate.atStartDayOfWeek().minusWeeks(52)
		val drawTargetDayRecordDataList = mutableListOf<DayRecordData>()

		while (indexDate.isBeforeOrSame(baseDate)) {
			val deepWorkLevel = recordMap[indexDate]?.getDeepWorkLevel() ?: DeepWorkTimesOnDay.DeepWorkLevel.LEVEL0

			val blockColor = colorResource(id = deepWorkLevel.colorId)
			val blockContainerColor = colorResource(id = deepWorkLevel.containerColorId)

			drawTargetDayRecordDataList.add(DayRecordData(blockColor, blockContainerColor, indexDate))

			indexDate = indexDate.plusDays(1)
		}

		fun DrawScope.getRecordOffset(index: Int, startOffsetX: Float, startOffsetY: Float): Offset {
			val row = index % DAY_NUMBERS_OF_WEEK
			val col = index / DAY_NUMBERS_OF_WEEK

			val x = yearRecordOffsetX.value + startOffsetX + col * yearRecordStyle.gapBetweenBlocks.toPx() + col * yearRecordStyle.recordBlockSize.toPx()
			val y = startOffsetY + row * yearRecordStyle.gapBetweenBlocks.toPx() + row * yearRecordStyle.recordBlockSize.toPx()

			return Offset(x, y)
		}

		fun DrawScope.drawSingleRecord(offset: Offset, dayRecordData: DayRecordData) {
			drawRoundRect(
				color = dayRecordData.blockColor,
				topLeft = offset,
				size = Size(yearRecordStyle.recordBlockSize.toPx(), yearRecordStyle.recordBlockSize.toPx()),
				cornerRadius = CornerRadius(yearRecordStyle.recordBlockRadius.toPx(), yearRecordStyle.recordBlockRadius.toPx())
			)

			drawRoundRect(
				color = dayRecordData.blockContainerColor,
				topLeft = offset,
				size = Size(yearRecordStyle.recordBlockSize.toPx(), yearRecordStyle.recordBlockSize.toPx()),
				cornerRadius = CornerRadius(yearRecordStyle.recordBlockRadius.toPx(), yearRecordStyle.recordBlockRadius.toPx()),
				style = Stroke()
			)
		}

		fun DrawScope.getMonthNameTextHeight(): Int {
			drawContext.canvas.nativeCanvas.apply {
				val textPaint = Paint().asFrameworkPaint().apply {
					isAntiAlias = true
					textSize = yearRecordStyle.recordBlockSize.toPx()
					color = yearRecordTextColor.toArgb()
					typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
				}
				val fontMetrics = textPaint.fontMetrics

				return (fontMetrics.descent - fontMetrics.ascent).roundToInt()
			}
		}

		fun DrawScope.drawMonthName(date: LocalDate, offsetX: Float, monthTextHeight: Int) {
			drawContext.canvas.nativeCanvas.apply {
				val textPaint = Paint().asFrameworkPaint().apply {
					isAntiAlias = true
					textSize = yearRecordStyle.recordBlockSize.toPx()
					color = yearRecordTextColor.toArgb()
					typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
				}
				val monthName = date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())

				drawText(
					monthName,
					offsetX,
					paddingTop.toPx() + monthTextHeight,
					textPaint
				)
			}
		}

		var offsetXLowerBound by remember {
			mutableStateOf(0f)
		}
		val offsetXUpperBound = 0f

		Canvas(
			modifier = Modifier
				.padding(10.dp)
				.fillMaxSize()
				.pointerInput(Unit) {
					val decay = splineBasedDecay<Float>(this)

					yearRecordOffsetX.snapTo(offsetXLowerBound)

					coroutineScope {
						while (true) {
							val pointerId = awaitPointerEventScope { awaitFirstDown().id }
							yearRecordOffsetX.stop()

							val velocityTracker = VelocityTracker()
							awaitPointerEventScope {
								horizontalDrag(pointerId) { change ->
									val horizontalDragOffset = yearRecordOffsetX.value + change.positionChange().x

									launch {
										yearRecordOffsetX.snapTo(horizontalDragOffset)
									}

									velocityTracker.addPosition(change.uptimeMillis, change.position)
									change.consumePositionChange()
								}
							}

							val velocity = velocityTracker.calculateVelocity().x
							yearRecordOffsetX.updateBounds(
								lowerBound = offsetXLowerBound,
								upperBound = offsetXUpperBound
							)
							launch {
								yearRecordOffsetX.animateDecay(velocity, decay)
							}
						}
					}
				}
		) {
			drawRoundRect(
				color = containerColor,
				size = Size(size.width, yearRecordHeight.toPx()),
				cornerRadius = CornerRadius(yearRecordStyle.containerRadius.toPx(), yearRecordStyle.containerRadius.toPx()),
				style = Stroke(width = yearRecordStyle.containerStrokeWidth.toPx())
			)

			val dayTextRect = Rect()
			var maxDayTextWidth = 0
			val monthTextHeight = getMonthNameTextHeight()
			val monthTextAreaHeight = monthTextHeight + 2 * yearRecordStyle.gapBetweenBlocks.toPx()
			val newYearRecordHeight = initialYearRecordHeight + monthTextAreaHeight.toDp()
			if (yearRecordHeight != newYearRecordHeight) {
				yearRecordHeight = newYearRecordHeight
			}

			val startOffsetY = paddingTop.toPx() + monthTextAreaHeight

			drawContext.canvas.nativeCanvas.apply {
				val dayToShowList = getDayToShowList()
				val textPaint = Paint().asFrameworkPaint().apply {
					isAntiAlias = true
					textSize = yearRecordStyle.recordBlockSize.toPx()
					color = yearRecordTextColor.toArgb()
					typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
				}

				dayToShowList.forEach {
					val targetText = it.first
					val targetTextIndex = it.second

					textPaint.getTextBounds(targetText, 0, targetText.length, dayTextRect)
					if (maxDayTextWidth < dayTextRect.width()) {
						maxDayTextWidth = dayTextRect.width()
					}

					drawText(
						targetText,
						paddingStart.toPx(),
						startOffsetY
								+ yearRecordStyle.recordBlockSize.toPx() * targetTextIndex
								+ yearRecordStyle.gapBetweenBlocks.toPx() * targetTextIndex
								+ dayTextRect.height(),
						textPaint
					)
				}
			}

			val dayTextAreaWidth = maxDayTextWidth + 2 * yearRecordStyle.gapBetweenBlocks.toPx()
			val startOffsetX = paddingStart.toPx() + dayTextAreaWidth

			val newOffsetXLowerBound = -(yearRecordWidth.toPx() + dayTextAreaWidth - size.width)
			if (offsetXLowerBound != newOffsetXLowerBound) {
				offsetXLowerBound = newOffsetXLowerBound
			}

			clipRect(
				left = startOffsetX,
				right = size.width,
				bottom = size.height
			) {
				drawTargetDayRecordDataList.forEachIndexed { index, dayRecordData ->
					val offset = getRecordOffset(index, startOffsetX, startOffsetY)
					drawSingleRecord(offset, dayRecordData)

					if (dayRecordData.date.isFirstDayOfMonth()) {
						drawMonthName(dayRecordData.date, offset.x, monthTextHeight)
					}
				}
			}
		}
	}
}

private fun getDayToShowList() = listOf<Pair<String, Int>>(
	DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()) to 1,
	DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()) to 3,
	DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()) to 5
)