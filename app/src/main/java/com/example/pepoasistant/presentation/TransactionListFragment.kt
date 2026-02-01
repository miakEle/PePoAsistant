package com.example.pepoasistant.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.data.CategoryRepositoryImp
import com.example.pepoasistant.data.DatabaseProvider
import com.example.pepoasistant.data.TransactionRepositoryImp
import kotlinx.coroutines.launch

class TransactionListFragment: Fragment(R.layout.fragment_transactions_list) {

    private lateinit var viewModel: TransactionListViewModel
    private lateinit var adapter: TransactionListAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DatabaseProvider.getDatabase(requireContext())

        val transactionRepo = TransactionRepositoryImp(db.transactionDao())
        val categoryRepo = CategoryRepositoryImp(db.categoryDao())
        val mapper = CategoryUiMapper(requireContext())

        val factory = TransactionViewModelFactory(transactionRepo, categoryRepo, mapper)
        viewModel = ViewModelProvider(this, factory)[TransactionListViewModel::class.java]

        adapter = TransactionListAdapter()

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        recycler.adapter = adapter
//        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Собираем Flow из ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.transactionListGrouped.collect { list ->
                adapter.submitList(list)
            }
        }
    }
}