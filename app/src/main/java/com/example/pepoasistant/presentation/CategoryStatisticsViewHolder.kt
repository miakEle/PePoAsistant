package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemCategotyStatusticsBinding

class CategoryStatisticsViewHolder(private val binding: ItemCategotyStatusticsBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(categoryStatisticsUi:CategoryStatisticsUi, maxWight: Int, maxAmount: Double)= with(binding){
        categoryName.text = categoryStatisticsUi.categoryName
        categoryIcon.setImageResource(categoryStatisticsUi.categoryIcon)
        categoryAmount.text = categoryStatisticsUi.amount.toString()
        categoryPercents.text = categoryStatisticsUi.percents.toString()
        categoryBar.setBackgroundColor(categoryStatisticsUi.color)
    }
}