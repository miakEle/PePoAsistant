package com.example.pepoasistant.presentation

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pepoasistant.R
import com.example.pepoasistant.domain.entities.Transaction
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

class TransactionListAdapter() :
    ListAdapter<TransactionListItem, RecyclerView.ViewHolder>(TransactionListItemDiffCallBack()) {


    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is TransactionListItem.MonthHeader -> TYPE_MONTH
            is TransactionListItem.DayHeader -> TYPE_DAY
            is TransactionListItem.TransactionRow -> TYPE_ITEM
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_MONTH -> MonthHeaderVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_month_header, parent, false)
            )

            TYPE_DAY -> DayHeaderVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_day_header, parent, false)
            )

            else -> TransactionVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_transaction, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TransactionListItem.MonthHeader -> (holder as MonthHeaderVH).bind(item)
            is TransactionListItem.DayHeader -> (holder as DayHeaderVH).bind(item)
            is TransactionListItem.TransactionRow -> (holder as TransactionVH).bind(item.transactionUi)
        }
    }


    class MonthHeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TransactionListItem.MonthHeader) {
            val monthName = Month.of(item.month)
                .getDisplayName(TextStyle.FULL, Locale("fi"))
                .replaceFirstChar { it.uppercase() }

            itemView.findViewById<TextView>(R.id.month_header).text =
                "$monthName ${item.year}"
        }
    }

    class DayHeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: TransactionListItem.DayHeader) {
            itemView.findViewById<TextView>(R.id.day_Header).text = item.date.toString()
        }
    }

    class TransactionVH(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(t: TransactionUi) {
            itemView.findViewById<TextView>(R.id.itemTitle).text = t.categoryName
            itemView.findViewById<TextView>(R.id.itemSubtitle).text = t.note
            itemView.findViewById<TextView>(R.id.itemValue).text = t.amount.toString()

        }
    }


    companion object {
        private const val TYPE_MONTH = 0
        private const val TYPE_DAY = 1
        private const val TYPE_ITEM = 2
    }
}
