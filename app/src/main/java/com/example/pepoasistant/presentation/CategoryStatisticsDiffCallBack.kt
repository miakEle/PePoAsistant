package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.DiffUtil

class CategoryStatisticsDiffCallBack: DiffUtil.ItemCallback<CategoryStatisticsUi>() {
    override fun areItemsTheSame(
        oldItem: CategoryStatisticsUi,
        newItem: CategoryStatisticsUi
    ): Boolean {
        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(
        oldItem: CategoryStatisticsUi,
        newItem: CategoryStatisticsUi
    ): Boolean {
        return  oldItem == newItem
    }
}