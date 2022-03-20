package com.jingom.deepworktracker.common.screen

import android.view.View
import androidx.core.view.ViewCompat

inline val View.isRtl: Boolean
	get() = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
