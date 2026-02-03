package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.DiffUtil

class PieSliceUiDiffCallBack: DiffUtil.ItemCallback<PieSliceUi>() {
    override fun areItemsTheSame(
        oldItem: PieSliceUi,
        newItem: PieSliceUi
    ): Boolean {
        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(
        oldItem: PieSliceUi,
        newItem: PieSliceUi
    ): Boolean {
        return oldItem == newItem
    }
}