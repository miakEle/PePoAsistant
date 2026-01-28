package com.example.pepoasistant.presentation


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pepoasistant.R

class TransactionInputFragment : Fragment(R.layout.fragment_transaction_input) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: initialize views here
        // val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        // saveButton.setOnClickListener { ... }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<View>(R.id.bottomAppBar).visibility = View.GONE
        requireActivity().findViewById<View>(R.id.bottomNavigation).visibility = View.GONE
        requireActivity().findViewById<View>(R.id.fab).visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<View>(R.id.bottomAppBar).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.bottomNavigation).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.fab).visibility = View.VISIBLE
    }


}