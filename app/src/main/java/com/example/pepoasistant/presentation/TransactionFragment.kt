package com.example.pepoasistant.presentation


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.data.DatabaseProvider
import com.example.pepoasistant.data.TransactionRepositoryImp
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate

class TransactionInputFragment : Fragment(R.layout.fragment_transaction_input) {

    private lateinit var viewModel: TransactionViewModel
    private var selectedCategoryId: Long? = null
    private var selectedDate: LocalDate = LocalDate.now()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- ViewModel ---
        val db = DatabaseProvider.getDatabase(requireContext())
        val repo = TransactionRepositoryImp(db.transactionDao())
        val factory = TransactionViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]

        // --- Views ---
        val categoryGrid = view.findViewById<RecyclerView>(R.id.categoryGrid)
        val amountInput = view.findViewById<TextInputEditText>(R.id.amountInput)
        val dateInput = view.findViewById<TextInputEditText>(R.id.dateInput)
        val noteInput = view.findViewById<TextInputEditText>(R.id.noteInput)
        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)

        // --- Category Grid ---
//        val adapter = CategoryAdapter { categoryId ->
//            selectedCategoryId = categoryId
//        }
//        categoryGrid.adapter = adapter
//        adapter.submitList(CategoryProvider.categories)

        // --- Date Picker ---
        dateInput.setText(selectedDate.toString())
        dateInput.setOnClickListener {
            val today = LocalDate.now()
            val picker = DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    selectedDate = LocalDate.of(y, m + 1, d)
                    dateInput.setText(selectedDate.toString())
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            )
            picker.show()
        }

        // --- Save Button ---
        saveButton.setOnClickListener {
            val categoryId = selectedCategoryId ?: return@setOnClickListener
            val amount = amountInput.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val note = noteInput.text?.toString()

            viewModel.insert(
                categoryId = categoryId,
                amount = amount,
                date = selectedDate,
                note = note
            )

            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
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