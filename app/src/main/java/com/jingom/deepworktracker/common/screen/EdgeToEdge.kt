package com.jingom.deepworktracker.common.screen

import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter

fun View.doOnApplyWindowInsets(
	f: (v: View, insets: WindowInsetsCompat, padding: Insets, margins: Insets) -> WindowInsetsCompat
) {
	// 현재 뷰의 패딩과 마진 상태 저장
	val padding = recordPadding()
	val margins = recordMargins()

	// WindowInset 이 적용되는 시점에 저장해두었던 뷰의 패딩과 마진상태도 함께 전달
	// 이렇게 원래 패딩과 마진 상태를 고려하지 않으면 상태바가 커지는 등의 오동작 발생
	ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
		f(view, insets, padding, margins)
	}

	requestApplyInsetsWhenAttached()
}

fun View.recordPadding() = Insets.of(paddingLeft, paddingTop, paddingRight, paddingBottom)

fun View.recordMargins(): Insets {
	val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return Insets.NONE
	return Insets.of(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

fun View.requestApplyInsetsWhenAttached() {
	if (isAttachedToWindow) {
		requestApplyInsets()
	} else {
		addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
			override fun onViewAttachedToWindow(v: View) {
				v.removeOnAttachStateChangeListener(this)
				v.requestApplyInsets()
			}

			override fun onViewDetachedFromWindow(v: View) = Unit
		})
	}
}

/**
 * Adjust padding or margin based on the current window insets for system bars like the navigation
 * bar and status bar. This will only ever increase padding or margins using the sum of the View's
 * original padding or margin plus the system insets.
 *
 * Specify which sides to adjust in XML using the binding expression `@{true}`. For example, the
 * following View will have its top margin set to the system's top inset, and it's left and right
 * padding set to the system's left and right insets plus 16dp.
 *
 * ```
 * <View
 *   android:layout_width="match_parent"
 *   android:layout_height="wrap_content"
 *   android:paddingLeft="16dp"
 *   android:paddingRight="16dp"
 *   app:marginTopSystemBars="@{true}"
 *   app:paddingLeftSystemBars="@{true}"
 *   app:paddingRightSystemBars="@{true}" />
 * ```
 *
 * Note that no insets will be consumed. If you need to consume insets, or need to apply some other
 * behavior, use [doOnApplyWindowInsets] directly instead.
 */
@BindingAdapter(
	"paddingLeftSystemBars",
	"paddingTopSystemBars",
	"paddingRightSystemBars",
	"paddingBottomSystemBars",
	"marginLeftSystemBars",
	"marginTopSystemBars",
	"marginRightSystemBars",
	"marginBottomSystemBars",
	requireAll = false
)
fun applySystemBars(
	view: View,
	padLeft: Boolean,
	padTop: Boolean,
	padRight: Boolean,
	padBottom: Boolean,
	marginLeft: Boolean,
	marginTop: Boolean,
	marginRight: Boolean,
	marginBottom: Boolean
) {
	val adjustPadding = padLeft || padTop || padRight || padBottom
	val adjustMargins = marginLeft || marginTop || marginRight || marginBottom
	if (!(adjustPadding || adjustMargins)) {
		return
	}

	view.doOnApplyWindowInsets { v, insets, padding, margins ->
		val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
		if (adjustPadding) {
			val systemLeft = if (padLeft) systemBars.left else 0
			val systemTop = if (padTop) systemBars.top else 0
			val systemRight = if (padRight) systemBars.right else 0
			val systemBottom = if (padBottom) systemBars.bottom else 0
			v.updatePadding(
				left = padding.left + systemLeft,
				top = padding.top + systemTop,
				right = padding.right + systemRight,
				bottom = padding.bottom + systemBottom,
			)
		}
		if (adjustMargins) {
			val systemLeft = if (marginLeft) systemBars.left else 0
			val systemTop = if (marginTop) systemBars.top else 0
			val systemRight = if (marginRight) systemBars.right else 0
			val systemBottom = if (marginBottom) systemBars.bottom else 0
			v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
				leftMargin = margins.left + systemLeft
				topMargin = margins.top + systemTop
				rightMargin = margins.right + systemRight
				bottomMargin = margins.bottom + systemBottom
			}

		}
		insets // Always return the insets, so that children can also use them.
	}
}
