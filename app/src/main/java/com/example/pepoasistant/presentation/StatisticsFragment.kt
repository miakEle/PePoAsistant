package com.example.pepoasistant.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.data.CategoryRepositoryImp
import com.example.pepoasistant.data.DatabaseProvider
import com.example.pepoasistant.data.TransactionRepositoryImp
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private lateinit var viewModel: StatisticsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DatabaseProvider.getDatabase(requireContext())
        val transactionRepo = TransactionRepositoryImp(db.transactionDao())
        val categoryRepo = CategoryRepositoryImp(db.categoryDao())
        val mapper = CategoryUiMapper(requireContext())

        val factory = TransactionViewModelFactory(transactionRepo, categoryRepo, mapper)
        viewModel = ViewModelProvider(this, factory)[StatisticsViewModel::class.java]

        val pieChartView = view.findViewById<PieChart>(R.id.pieChart)

        val adapter = PieSliceAdapter()

        val recycler = view.findViewById<RecyclerView>(R.id.pieChart_info)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pieData.collect { slices ->

                    val entries = slices.map { slice ->
                        PieEntry(slice.amount.toFloat(), slice.categoryName)
                    }

                    val dataSet = PieDataSet(entries, "")
                    dataSet.colors = slices.map { it.color }

                    val pieData = PieData(dataSet)

                    pieChartView.data = pieData
                    pieChartView.apply {
                        description.isEnabled = false
                        setDrawEntryLabels(false)
                        legend.isEnabled = false
                        dataSet.setDrawValues(false)
                        animateY(400)
                        isDrawHoleEnabled = true
                        holeRadius = 55f
                        transparentCircleRadius = 60f
                        setHoleColor(Color.TRANSPARENT)
                        setDrawEntryLabels(false)
                        legend.isEnabled = false
                        isRotationEnabled = false
                        isHighlightPerTapEnabled = false
                    }

                    pieChartView.invalidate()

                    pieChartView.invalidate()
                    adapter.submitList(slices)
                }
            }
        }
    }
}
