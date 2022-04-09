package com.jingom.deepworktracker.feature_tracking.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jingom.deepworktracker.common.screen.views.ViewMvxFactory
import com.jingom.deepworktracker.feature_tracking.domain.usecase.GetDeepWorksUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashBoardFragment: Fragment() {

	private val deepWorkYearRecordViewModel: DeepWorkYearRecordViewModel by viewModels()
	private val deepWorksViewModel: DeepWorksViewModel by viewModels()

	@Inject lateinit var viewMvxFactory: ViewMvxFactory
	private lateinit var dashBoardViewMvx: DashBoardViewMvx

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		dashBoardViewMvx = viewMvxFactory.getDashBoardMvx(viewLifecycleOwner, container, deepWorkYearRecordViewModel, deepWorksViewModel)
		return dashBoardViewMvx.rootView
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}
}