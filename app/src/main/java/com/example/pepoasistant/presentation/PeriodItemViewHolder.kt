package com.example.pepoasistant.presentation

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.databinding.ItemPeriodBinding

class PeriodItemViewHolder(
    private val binding: ItemPeriodBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PeriodListItem.Period, onClick: (PeriodListItem.Period) -> Unit) {
        binding.cardTitle.text = item.label
        binding.card.isChecked = item.selected

        if (item.selected) {
            binding.bar.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green_300))
//            binding.card.strokeWidth = 4
//            binding.card.alpha = 1f
//            binding.cardTitle.setTypeface(null, android.graphics.Typeface.BOLD)
        } else {
            binding.bar.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.black))
//            binding.card.strokeWidth = 0
//            binding.card.alpha = 0.5f
//            binding.cardTitle.setTypeface(null, android.graphics.Typeface.NORMAL)
        }

        binding.root.setOnClickListener {
            onClick(item)
        }
    }
}