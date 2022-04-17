package com.jingom.deepworktracker.feature_tracking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jingom.deepworktracker.common.ui.DeepworkTrackerTheme
import com.jingom.deepworktracker.feature_tracking.presentation.ui.DashBoardScreen
import com.jingom.deepworktracker.feature_tracking.presentation.ui.DeepWorkScreen
import com.jingom.deepworktracker.feature_tracking.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			MainActivityScreen()
		}

		initSystemUIColor()

		initWindowEdgeToEdgeConfig()
	}

	private fun initSystemUIColor() {
		WindowInsetsControllerCompat(window, window.decorView).apply {
			isAppearanceLightStatusBars = true
			isAppearanceLightNavigationBars = true
		}
	}

	private fun initWindowEdgeToEdgeConfig() {
//		WindowCompat.setDecorFitsSystemWindows(window, false)
	}

	@Preview
	@Composable
	fun MainActivityScreen() {
		DeepworkTrackerTheme {
			Surface(color = MaterialTheme.colors.background) {

				val navController = rememberNavController()
				NavHost(
					navController = navController,
					startDestination = Screen.DashBoardScreen.route
				) {
					composable(route = Screen.DashBoardScreen.route) {
//						DashBoardScreen(navController = navController)
						DeepWorkScreen(navController = navController)
					}
				}
			}
		}
	}
}