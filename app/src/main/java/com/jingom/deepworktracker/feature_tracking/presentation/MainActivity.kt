package com.jingom.deepworktracker.feature_tracking.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.jingom.deepworktracker.R
import com.jingom.deepworktracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)

		setContentView(binding.root)

		initSystemUIColor()

		initWindowEdgeToEdgeConfig()
	}

	private fun initSystemUIColor() {
		WindowInsetsControllerCompat(window, binding.root).apply {
			isAppearanceLightStatusBars = true
			isAppearanceLightNavigationBars = true
		}
	}

	private fun initWindowEdgeToEdgeConfig() {
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
}