package com.example.pepoasistant.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pepoasistant.databinding.ItemCategotyStatisticsBinding

class CategoryStatisticsAdapter :
    ListAdapter<CategoryStatisticsUi, CategoryStatisticsViewHolder>(CategoryStatisticsDiffCallBack()) {

    private var maxAmount: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryStatisticsViewHolder {
        val binding = ItemCategotyStatisticsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryStatisticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryStatisticsViewHolder, position: Int) {
        holder.bind(currentList[position], maxAmount)
    }

    override fun submitList(list: List<CategoryStatisticsUi>?) {
        maxAmount = list?.maxOfOrNull { it.amount } ?: 0.0
        super.submitList(list)
    }
}
