package com.example.pepoasistant.presentation

sealed class PeriodListItem {
    data class Period(
        val offset: Int,
        val label: String,
        val year: Int,
        val month: Int,
        val selected: Boolean = false
    ) : PeriodListItem()

    object AddMore : PeriodListItem()
}