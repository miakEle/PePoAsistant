package com.example.pepoasistant.presentation

import androidx.recyclerview.widget.DiffUtil

class TransactionListItemDiffCallBack : DiffUtil.ItemCallback<TransactionListItem>() {
    override fun areItemsTheSame(old: TransactionListItem, new: TransactionListItem): Boolean {
        return when {
            old is TransactionListItem.MonthHeader && new is TransactionListItem.MonthHeader ->
                old.year == new.year && old.month == new.month

            old is TransactionListItem.DayHeader && new is TransactionListItem.DayHeader ->
                old.date == new.date

            old is TransactionListItem.TransactionRow && new is TransactionListItem.TransactionRow ->
                old.transactionUi.id == new.transactionUi.id

            else -> false
        }
    }

    override fun areContentsTheSame(old: TransactionListItem, new: TransactionListItem): Boolean =
        old == new
}
