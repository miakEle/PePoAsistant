package com.example.pepoasistant.presentation

import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemPiechartInfoBinding

class PieSliceViewHolder(
    private val binding: ItemPiechartInfoBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PieSliceUi) = with(binding){

        val drawable = binding.itemIcon.background.mutate() as GradientDrawable
        drawable.setColor(item.color)

        binding.itemTitle.text = item.categoryName
        binding.itemValue.text = String.format("%.0f%%", item.percent * 100)


    }
}