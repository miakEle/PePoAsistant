package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemPeriodBinding

class PeriodItemViewHolder(
    private val binding: ItemPeriodBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PeriodListItem.Period, onClick: (PeriodListItem.Period) -> Unit) {
        binding.cardTitle.text = item.label
        binding.card.isChecked = item.selected

        if (item.selected) {
            binding.card.strokeWidth = 4
            binding.card.alpha = 1f
            binding.cardTitle.setTypeface(null, android.graphics.Typeface.BOLD)
        } else {
            binding.card.strokeWidth = 0
            binding.card.alpha = 0.5f
            binding.cardTitle.setTypeface(null, android.graphics.Typeface.NORMAL)
        }

        binding.root.setOnClickListener {
            onClick(item)
        }
    }
}