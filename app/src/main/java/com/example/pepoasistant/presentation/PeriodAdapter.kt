package com.example.pepoasistant.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.databinding.ItemAddMoreBinding
import com.example.pepoasistant.databinding.ItemPeriodBinding

class PeriodAdapter(
    private val onPeriodClick: (PeriodListItem.Period) -> Unit,
    private val onAddMoreClick: () -> Unit
) : ListAdapter<PeriodListItem, RecyclerView.ViewHolder>(PeriodDiffCallBack()) {


    companion object {
        private const val TYPE_PERIOD = 0
        private const val TYPE_ADD_MORE = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_PERIOD -> PeriodItemViewHolder(
                ItemPeriodBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> AddMoreItemViewHolder(
                ItemAddMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PeriodItemViewHolder -> holder.bind(
                getItem(position) as PeriodListItem.Period,
                onPeriodClick
            )

            is AddMoreItemViewHolder -> holder.itemView.setOnClickListener { onAddMoreClick() }
        }
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is PeriodListItem.Period -> TYPE_PERIOD
            is PeriodListItem.AddMore -> TYPE_ADD_MORE
        }

}