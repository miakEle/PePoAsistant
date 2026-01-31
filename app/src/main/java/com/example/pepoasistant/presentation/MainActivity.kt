package com.example.pepoasistant.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.pepoasistant.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.layout_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            findNavController(R.id.fragment_container)
                .navigate(R.id.fragment_input)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    findNavController(R.id.fragment_container)
                        .navigate(R.id.fragment_transaction_list)
                    true
                }
                R.id.nav_home -> {
                        findNavController(R.id.fragment_container)
                            .navigate(R.id.statisticsFragment)
                        true
                }
                else -> false
            }
        }



        lifecycleScope.launch {  }



    }
}