package com.jingom.deepworktracker.feature_tracking.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.datetime.atStartDayOfWeek
import com.jingom.deepworktracker.common.datetime.isBeforeOrSame
import com.jingom.deepworktracker.common.screen.BaseCustomView
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import com.jingom.deepworktracker.feature_tracking.presentation.LastYearDeepWorkData

class YearRecordView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : BaseCustomView(context, attrs, defStyleAttr) {

	var lastYearDeepWorkData: LastYearDeepWorkData? = null
	private val start = Offset(0f, 0f)
	private val padding = 10.dpToPx()
	private val recordBlockSize = 10.dpToPx()
	private val gapBetweenBlocks = 5.dpToPx()
	private val contentHeight = (recordBlockSize * 7 + gapBetweenBlocks * (7 - 1))
	private val contentWidth = (recordBlockSize * (52 + 1) + gapBetweenBlocks * 52)
	private val recordBlockRadius = 2.dpToPx()

	private val containerPaint = Paint().apply {
		color = ContextCompat.getColor(context, R.color.deepwork_year_record_container)
		style = Paint.Style.STROKE
		strokeWidth = 2.dpToPx()
	}

	private val recordPaint = Paint().apply {
		style = Paint.Style.FILL
		isAntiAlias = true
	}

	private val recordContainerPaint = Paint().apply {
		style = Paint.Style.STROKE
		strokeWidth = 1.dpToPx()
		isAntiAlias = true
	}

	override fun onDraw(canvas: Canvas) = canvas.run {
		drawContainer()
		drawRecord()
	}

	private fun Canvas.drawContainer() {
		drawRoundRect(
			start.x,
			start.y,
			start.x + width.toFloat(),
			start.y + height.toFloat(),
			recordBlockRadius,
			recordBlockRadius,
			containerPaint
		)
	}

	private fun Canvas.drawRecord() {
		val data = this@YearRecordView.lastYearDeepWorkData ?: LastYearDeepWorkData()
		val recordMap = data.deepWorkRecordMap
		val baseDate = data.baseDate
		var indexDate = baseDate.atStartDayOfWeek().minusWeeks(52)

		var index = 0
		while (indexDate.isBeforeOrSame(baseDate)) {
			val deepWorkLevel = recordMap[indexDate]?.getDeepWorkLevel() ?: DeepWorkTimesOnDay.DeepWorkLevel.LEVEL0
			val offset = index.getRecordOffset()

			drawSingleRecord(offset, deepWorkLevel)

			index++
			indexDate = indexDate.plusDays(1)
		}
	}

	private fun Int.getRecordOffset(): Offset {
		val row = this % 7
		val col = this / 7

		val x = padding + col * gapBetweenBlocks + col * recordBlockSize
		val y = padding + row * gapBetweenBlocks + row * recordBlockSize

		return Offset(x, y)
	}

	private fun Canvas.drawSingleRecord(offset: Offset, deepWorkLevel: DeepWorkTimesOnDay.DeepWorkLevel) {
		recordPaint.color = ContextCompat.getColor(context, deepWorkLevel.colorId)
		recordContainerPaint.color = ContextCompat.getColor(context, deepWorkLevel.containerColorId)

		drawRoundRect(
			offset.x,
			offset.y,
			offset.x + recordBlockSize,
			offset.y + recordBlockSize,
			recordBlockRadius,
			recordBlockRadius,
			recordPaint
		)

		drawRoundRect(
			offset.x,
			offset.y,
			offset.x + recordBlockSize,
			offset.y + recordBlockSize,
			recordBlockRadius,
			recordBlockRadius,
			recordContainerPaint
		)
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val desiredWidth = (contentWidth + 2 * padding).toInt()
		val desiredHeight = (contentHeight + 2 * padding).toInt()

		setMeasuredDimension(resolveSize(desiredWidth, widthMeasureSpec), resolveSize(desiredHeight, heightMeasureSpec))
	}
}

@BindingAdapter("record")
fun YearRecordView.setRecord(lastYearDeepWorkData: LastYearDeepWorkData?) {
	this.lastYearDeepWorkData = lastYearDeepWorkData
	invalidate()
}