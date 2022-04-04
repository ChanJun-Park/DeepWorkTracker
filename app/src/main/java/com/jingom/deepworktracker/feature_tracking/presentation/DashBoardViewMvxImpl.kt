package com.jingom.deepworktracker.feature_tracking.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.screen.views.BaseObservableViewMvx
import com.jingom.deepworktracker.databinding.LayoutDashBoardBinding
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWork
import com.jingom.deepworktracker.feature_tracking.domain.model.DeepWorkTimesOnDay
import java.time.LocalDate

class DashBoardViewMvxImpl(
	lifecycleOwner: LifecycleOwner,
	layoutInflater: LayoutInflater,
	parent: ViewGroup?
): BaseObservableViewMvx<DashBoardViewMvx.Listener>(
	lifecycleOwner,
	layoutInflater,
	parent,
	R.layout.layout_dash_board
), DashBoardViewMvx {
	private val binding: LayoutDashBoardBinding by dataBindings(LayoutDashBoardBinding::bind)

	override fun bindDeepWorks(deepWorks: List<DeepWork>) {
		TODO("Not yet implemented")
	}

	override fun bindDeepWorkTimesOnDayInLastYear(lastYearDeepWorkData: LastYearDeepWorkData) {
		TODO("Not yet implemented")
	}
}