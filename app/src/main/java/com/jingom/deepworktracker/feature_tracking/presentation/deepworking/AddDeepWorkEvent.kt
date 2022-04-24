package com.jingom.deepworktracker.feature_tracking.presentation.deepworking

sealed class AddDeepWorkEvent {
	data class EnteredTitle(val value: String) : AddDeepWorkEvent()
	object SaveDeepWork : AddDeepWorkEvent()
}
