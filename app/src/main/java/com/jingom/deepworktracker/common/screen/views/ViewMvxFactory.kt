package com.jingom.deepworktracker.common.screen.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.jingom.deepworktracker.feature_tracking.presentation.DashBoardViewMvx
import com.jingom.deepworktracker.feature_tracking.presentation.DashBoardViewMvxImpl
import com.jingom.deepworktracker.feature_tracking.presentation.DeepWorkYearRecordViewModel
import com.jingom.deepworktracker.feature_tracking.presentation.DeepWorksViewModel
import javax.inject.Inject

class ViewMvxFactory @Inject constructor(
	private val layoutInflater: LayoutInflater
) {
	fun getDashBoardMvx(
		lifecycleOwner: LifecycleOwner,
		parent: ViewGroup?,
		yearRecordViewModel: DeepWorkYearRecordViewModel,
		deepWorksViewModel: DeepWorksViewModel
	): DashBoardViewMvx {
		return DashBoardViewMvxImpl(lifecycleOwner, layoutInflater, parent, yearRecordViewModel, deepWorksViewModel)
	}
}