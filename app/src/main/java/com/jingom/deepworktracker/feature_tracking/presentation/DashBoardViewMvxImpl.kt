package com.jingom.deepworktracker.feature_tracking.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.common.screen.views.BaseObservableViewMvx
import com.jingom.deepworktracker.databinding.LayoutDashBoardBinding

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
}