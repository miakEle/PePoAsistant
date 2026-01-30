package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.DiffUtil

class CategoryUiDiffCallBack: DiffUtil.ItemCallback<CategoryUi>() {
    override fun areItemsTheSame(
        oldItem: CategoryUi,
        newItem: CategoryUi
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryUi,
        newItem: CategoryUi
    ): Boolean {
        return oldItem == newItem
    }
}