package com.example.pepoasistant.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pepoasistant.databinding.ItemPiechartInfoBinding

class PieSliceAdapter : ListAdapter<PieSliceUi, PieSliceViewHolder>(PieSliceUiDiffCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PieSliceViewHolder {
        val binding = ItemPiechartInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PieSliceViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PieSliceViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}