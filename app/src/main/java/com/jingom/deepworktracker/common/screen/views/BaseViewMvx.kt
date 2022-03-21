package com.jingom.deepworktracker.common.screen.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseViewMvx(
	val lifecycleOwner: LifecycleOwner,
	layoutInflater: LayoutInflater,
	parent: ViewGroup?,
	@LayoutRes layoutResId: Int
): ViewMvx {

	override val rootView: View = layoutInflater.inflate(layoutResId, parent, false)

	protected val context: Context
		get() = rootView.context

	protected fun <BindingT : ViewDataBinding> dataBindings(
		bind: (View) -> BindingT
	) = object : Lazy<BindingT> {

		private var cached: BindingT? = null

		private val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_DESTROY) {
				cached = null
			}
		}

		override val value: BindingT
			get() = cached ?: bind(rootView).also {
				it.lifecycleOwner = lifecycleOwner
				lifecycleOwner.lifecycle.addObserver(observer)
				cached = it
			}

		override fun isInitialized() = cached != null
	}
}