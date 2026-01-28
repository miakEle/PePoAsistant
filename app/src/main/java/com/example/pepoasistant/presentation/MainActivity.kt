package com.example.pepoasistant.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pepoasistant.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var mainNavController: NavController
    private lateinit var overlayNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        // Основной NavHost
        val mainHost = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        mainNavController = mainHost.navController

        // Overlay NavHost
        val overlayHost = supportFragmentManager
            .findFragmentById(R.id.fragment_container_overlay) as NavHostFragment
        overlayNavController = overlayHost.navController

        // FAB → открыть overlay-фрагмент
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findViewById<View>(R.id.fragment_container_overlay).visibility = View.VISIBLE
            overlayNavController.navigate(R.id.transactionInputFragment)
        }

        // Когда overlay закрывается → скрываем контейнер
        overlayNavController.addOnDestinationChangedListener { _, dest, _ ->
            if (dest.id == R.id.emptyOverlayFragment) {
                findViewById<View>(R.id.fragment_container_overlay).visibility = View.GONE
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            if (findViewById<View>(R.id.fragment_container_overlay).visibility == View.VISIBLE) {
                // Close overlay
                findViewById<View>(R.id.fragment_container_overlay).visibility = View.GONE
            } else {
                // Default back behavior
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }

    }
}