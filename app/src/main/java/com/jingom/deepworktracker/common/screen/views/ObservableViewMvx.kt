package com.jingom.deepworktracker.common.screen.views

interface ObservableViewMvx<ListenerType>: ViewMvx {
	fun registerListener(listener: ListenerType)
	fun unregisterListener(listener: ListenerType)
}