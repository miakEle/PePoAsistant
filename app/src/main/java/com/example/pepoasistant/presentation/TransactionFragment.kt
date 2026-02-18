package com.example.pepoasistant.presentation


import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.pepoasistant.R
import com.example.pepoasistant.data.CategoryRepositoryImp
import com.example.pepoasistant.data.DatabaseProvider
import com.example.pepoasistant.data.TransactionRepositoryImp
import com.example.pepoasistant.databinding.FragmentTransactionInputBinding
import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.entities.TypeOfCategory
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionInputFragment : Fragment() {

    private var _binding: FragmentTransactionInputBinding? = null
    val binding: FragmentTransactionInputBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private lateinit var viewModel: TransactionViewModel

    private var selectedCategoryId: Long? = null

    private var selectedDate: LocalDate = LocalDate.now()

    private val args: TransactionInputFragmentArgs by navArgs()

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

        val transactionId = args.transactionId

        if (transactionId != -1L) {
            viewModel.loadTransaction(transactionId)
        }

        val adapter = CategoryAdapter { category ->
            selectedCategoryId = category.id
            viewModel.onCategoryClicked(category.id)
        }
        binding.categoryGrid.adapter = adapter

        binding.cardExpense.isChecked = true
        binding.cardIncome.isChecked = false

        binding.cardExpense.setOnClickListener {
            binding.cardExpense.isChecked = true
            binding.cardIncome.isChecked = false
            viewModel.selectType(TypeOfCategory.EXPENSE)
        }

        binding.cardIncome.setOnClickListener {
            binding.cardIncome.isChecked = true
            binding.cardExpense.isChecked = false
            viewModel.selectType(TypeOfCategory.INCOME)
        }

        lifecycleScope.launch {
            viewModel.transaction.collect { transaction ->
                if (transaction != null) {
                    viewModel.onCategoryClicked(transaction.id)
                    loadEditingMode(transaction)
                }
            }
        }


        lifecycleScope.launch {
            viewModel.typeOfCategory.collect { categoryType ->
                when (categoryType) {
                    TypeOfCategory.EXPENSE -> {
                        binding.cardExpense.isChecked = true
                        binding.cardIncome.isChecked = false
                    }

                    TypeOfCategory.INCOME -> {
                        binding.cardIncome.isChecked = true
                        binding.cardExpense.isChecked = false
                    }
                }
                viewModel.getAllCategoriesByType()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { categories ->
                adapter.submitList(categories)

                val tx = viewModel.transaction.value
                if (tx != null) {
                    val category = categories.firstOrNull { it.id == tx.categoryId }
                    category?.let {
                        selectedCategoryId = it.id
                        viewModel.onCategoryClicked(it.id)
                        viewModel.selectType(it.type)
                    }
                }

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
            if (!validateInputs()) return@setOnClickListener
            val amount =
                binding.amountInput.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val note = binding.noteInput.text?.toString()

            if (transactionId != -1L) {
                viewModel.editTransaction(
                    id = transactionId,
                    categoryId = selectedCategoryId!!,
                    amount = amount,
                    date = selectedDate,
                    note = note
                )
            } else {
                viewModel.insert(
                    categoryId = selectedCategoryId!!,
                    amount = amount,
                    date = selectedDate,
                    note = note
                )
            }
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadEditingMode(transaction: Transaction) {
        binding.amountInput.setText(transaction.amount.toString())
        binding.dateInput.setText(transaction.date.toString())
        binding.noteInput.setText(transaction.note)
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

    private fun validateInputs(): Boolean {
        if (selectedCategoryId == null) {
            binding.categoryError.visibility = View.VISIBLE
            binding.categoryError.text = "Please select a category"
            return false
        } else {
            binding.categoryError.visibility = View.GONE
        }

        val amountText = binding.amountInput.text.toString()
        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            binding.amountInput.error = "Enter a valid amount"
            return false
        }

        return true
    }


}