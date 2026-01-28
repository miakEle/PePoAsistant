package com.example.pepoasistant.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.pepoasistant.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            findNavController(R.id.fragment_container)
                .navigate(R.id.fragment_input)
        }

        lifecycleScope.launch {  }



    }
}