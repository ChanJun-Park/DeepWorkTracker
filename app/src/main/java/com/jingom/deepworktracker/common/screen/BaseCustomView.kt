package com.jingom.deepworktracker.common.screen

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

abstract class BaseCustomView  @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

	data class Offset(
		var x: Float,
		var y: Float
	)

	fun Float.dpToPx(): Float = TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		this,
		context.resources.displayMetrics
	)

	fun Double.dpToPx(): Float = TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		this.toFloat(),
		context.resources.displayMetrics
	)

	fun Int.dpToPx(): Float = TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		this.toFloat(),
		context.resources.displayMetrics
	)
}