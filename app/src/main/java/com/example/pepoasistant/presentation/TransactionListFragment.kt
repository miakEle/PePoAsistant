package com.example.pepoasistant.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.data.CategoryRepositoryImp
import com.example.pepoasistant.data.DatabaseProvider
import com.example.pepoasistant.data.TransactionRepositoryImp
import com.example.pepoasistant.databinding.FragmentTransactionsListBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionListFragment : Fragment() {

    private lateinit var viewModel: TransactionListViewModel
    private lateinit var adapter: TransactionListAdapter
    private var _binding: FragmentTransactionsListBinding? = null

    val binding: FragmentTransactionsListBinding
        get() = _binding ?: throw RuntimeException("FragmentTransactionsListBinding == null")

    val months = listOf(
        "tammi", "helmi", "maalis", "huhti", "touko", "kesä",
        "heinä", "elo", "syys", "loka", "marras", "joulu"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DatabaseProvider.getDatabase(requireContext())

        val transactionRepo = TransactionRepositoryImp(db.transactionDao())
        val categoryRepo = CategoryRepositoryImp(db.categoryDao())
        val mapper = CategoryUiMapper(requireContext())
        val transactionMapper = TransactionUiMapper()

        val factory = TransactionViewModelFactory(transactionRepo, categoryRepo, mapper)
        viewModel = ViewModelProvider(this, factory)[TransactionListViewModel::class.java]

        adapter = TransactionListAdapter { id ->
            findNavController().navigate(TransactionListFragmentDirections
                .actionFragmentTransactionListToFragmentInput(id)
            )
        }

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        recycler.adapter = adapter

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.layoutPosition
                val transaction = adapter.currentList[position] as TransactionListItem.TransactionRow
                viewModel.deleteTransaction(transactionMapper.toData(transaction.transactionUi))
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(recycler)

        binding.tvMonth.setOnClickListener {
            BottomSheetMonthYear(
                initialYear = LocalDate.now().year,
                initialMonth = LocalDate.now().monthValue
            ) { year, month ->
                viewModel.setSelectedYearAndMonth(year, month)
            }.show(parentFragmentManager, "monthYearPicker")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedYear.collect { year ->
                binding.tvYear.text = year.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedMonth.collect { month ->
                binding.tvMonth.text = months[month - 1]
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.transactionListGrouped.collect { list ->
                adapter.submitList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.amountOfIncome.collect { amount ->
                binding.tvAmountIncome.text = amount.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.amountOfIExpense.collect { amount ->
                binding.tvAmountExpense.text = amount.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.balance.collect { amount ->
                if (amount < 0) {
                    binding.tvAmountSaldo.text = "- ${kotlin.math.abs(amount)}"
                } else {
                    binding.tvAmountSaldo.text = amount.toString()
                }


            }
        }



    }
}