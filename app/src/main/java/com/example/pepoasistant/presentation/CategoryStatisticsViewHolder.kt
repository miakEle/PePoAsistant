package com.example.pepoasistant.presentation

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemCategotyStatisticsBinding

class CategoryStatisticsViewHolder(private val binding: ItemCategotyStatisticsBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(categoryStatisticsUi:CategoryStatisticsUi, maxAmount: Double)= with(binding){
        categoryName.text = categoryStatisticsUi.categoryName

        val ctx = root.context
        val resId = ctx.resources.getIdentifier(
            categoryStatisticsUi.categoryIcon,
            "drawable",
            ctx.packageName
        )
        if (resId != 0) {
            categoryIcon.setImageResource(resId)
        } else {
            categoryIcon.setImageResource(android.R.color.transparent)
        }

        categoryAmount.text = categoryStatisticsUi.amount.toString()
        categoryPercents.text = String.format("%.0f%%", categoryStatisticsUi.percents * 100)

        progressBar.setProgress(((categoryStatisticsUi.amount / maxAmount)*100).toInt())
        progressBar.progressTintList = ColorStateList.valueOf(categoryStatisticsUi.color)
    }
}