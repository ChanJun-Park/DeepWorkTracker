package com.jingom.deepworktracker.feature_tracking.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

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
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
}