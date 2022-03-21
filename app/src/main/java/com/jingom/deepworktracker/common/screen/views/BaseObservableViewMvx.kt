package com.jingom.deepworktracker.common.screen.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner

abstract class BaseObservableViewMvx<ListenerType>(
	lifecycleOwner: LifecycleOwner,
	layoutInflater: LayoutInflater,
	parent: ViewGroup?,
	@LayoutRes layoutResId: Int
): BaseViewMvx(lifecycleOwner, layoutInflater, parent, layoutResId), ObservableViewMvx<ListenerType> {

	private val listeners = HashSet<ListenerType>()

	override fun registerListener(listener: ListenerType) {
		listeners.add(listener)
	}

	override fun unregisterListener(listener: ListenerType) {
		listeners.remove(listener)
	}
}