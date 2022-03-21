package com.jingom.deepworktracker.common.screen.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.jingom.deepworktracker.feature_tracking.presentation.DashBoardViewMvx
import com.jingom.deepworktracker.feature_tracking.presentation.DashBoardViewMvxImpl
import javax.inject.Inject

class ViewMvxFactory @Inject constructor(
	private val layoutInflater: LayoutInflater
) {
	fun getDashBoardMvx(lifecycleOwner: LifecycleOwner, parent: ViewGroup?): DashBoardViewMvx {
		return DashBoardViewMvxImpl(lifecycleOwner, layoutInflater, parent)
	}
}