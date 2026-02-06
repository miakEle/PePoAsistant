package com.example.pepoasistant.presentation

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.databinding.ItemCategoryBinding

class CategoryViewHolder(
    private val binding: ItemCategoryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryUi) = with(binding){
        categoryName.text = item.name
        categoryIcon.setImageResource(item.icon)

        if (item.isSelected){
            categoryIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.green_300))
            categoryName.setTextColor(ContextCompat.getColor(root.context, R.color.green_300))
        } else {
            categoryIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.white))
            categoryName.setTextColor(ContextCompat.getColor(root.context, R.color.white))
        }
    }
}