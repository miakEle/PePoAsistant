package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.DiffUtil

class PeriodDiffCallBack : DiffUtil.ItemCallback<PeriodListItem>() {

    override fun areItemsTheSame(oldItem: PeriodListItem, newItem: PeriodListItem): Boolean {
        return when {
            oldItem is PeriodListItem.Period && newItem is PeriodListItem.Period ->
                oldItem.offset == newItem.offset

            oldItem is PeriodListItem.AddMore && newItem is PeriodListItem.AddMore ->
                true

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: PeriodListItem, newItem: PeriodListItem): Boolean {
        return oldItem == newItem
    }
}
