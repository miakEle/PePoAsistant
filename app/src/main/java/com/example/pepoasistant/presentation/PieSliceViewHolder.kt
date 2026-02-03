package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemPiechartInfoBinding

class PieSliceViewHolder(
    private val binding: ItemPiechartInfoBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PieSliceUi) = with(binding){

        binding.itemIcon.setBackgroundColor(item.color)
        binding.itemTitle.text = item.categoryName
        binding.itemValue.text = item.amount.toString()

    }
}