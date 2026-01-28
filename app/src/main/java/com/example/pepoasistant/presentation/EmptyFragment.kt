package com.example.pepoasistant.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pepoasistant.R

class EmptyFragment: Fragment(R.layout.fragment_empty_overlay) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: initialize views here
        // val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        // saveButton.setOnClickListener { ... }
    }
}