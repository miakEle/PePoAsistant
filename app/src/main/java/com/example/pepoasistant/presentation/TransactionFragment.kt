package com.example.pepoasistant.presentation


import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pepoasistant.R
import com.example.pepoasistant.data.CategoryRepositoryImp
import com.example.pepoasistant.data.DatabaseProvider
import com.example.pepoasistant.data.TransactionRepositoryImp
import com.example.pepoasistant.databinding.FragmentTransactionInputBinding
import com.example.pepoasistant.domain.entities.TypeOfCategory
import java.time.LocalDate

class TransactionInputFragment : Fragment() {

    private var _binding: FragmentTransactionInputBinding? = null
    val binding: FragmentTransactionInputBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private lateinit var viewModel: TransactionViewModel

    private var selectedCategoryId: Long? = null
    private var selectedDate: LocalDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DatabaseProvider.getDatabase(requireContext())
        val transactionRepo = TransactionRepositoryImp(db.transactionDao())
        val categoryRepo = CategoryRepositoryImp(db.categoryDao())
        val mapper = CategoryUiMapper(requireContext())

        val factory = TransactionViewModelFactory(transactionRepo, categoryRepo, mapper)
        viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]

        val adapter = CategoryAdapter { category ->
            viewModel.onCategoryClicked(category.id)
        }
        binding.categoryGrid.adapter = adapter

        viewModel.getAllCategoriesByType(TypeOfCategory.EXPENSE)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { categories ->
                adapter.submitList(categories)
            }
        }


        // --- Date Picker ---
        binding.dateInput.setText(selectedDate.toString())
        binding.dateInput.setOnClickListener {
            val today = LocalDate.now()
            val picker = DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    selectedDate = LocalDate.of(y, m + 1, d)
                    binding.dateInput.setText(selectedDate.toString())
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            )
            picker.show()
        }

        binding.saveButton.setOnClickListener {
//            val categoryId = selectedCategoryId ?: return@setOnClickListener
            val amount =
                binding.amountInput.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val note = binding.noteInput.text?.toString()

            viewModel.insert(
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}