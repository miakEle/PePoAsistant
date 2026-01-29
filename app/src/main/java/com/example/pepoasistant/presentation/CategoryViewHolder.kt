package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemCategoryBinding

class CategoryViewHolder(
    private val binding: ItemCategoryBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryUi) = with(binding){
        categoryName.text = item.name
        categoryIcon.setImageResource(item.icon)
    }
}