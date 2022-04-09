package com.jingom.deepworktracker.feature_tracking.presentation.util

sealed class Screen(val route: String) {
	object DashBoardScreen: Screen("dash_board_screen")
}