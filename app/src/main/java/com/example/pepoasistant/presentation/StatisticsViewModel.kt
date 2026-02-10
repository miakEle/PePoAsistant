package com.example.pepoasistant.presentation

import androidx.lifecycle.ViewModel
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.entities.TypeOfCategory
import com.example.pepoasistant.domain.repositories.CategoryRepository
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDate

class StatisticsViewModel(
    private val repository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val monthsNames = listOf(
        "tammi", "helmi", "maalis", "huhti", "touko", "kesä",
        "heinä", "elo", "syys", "loka", "marras", "joulu"
    )

    // -----------------------------
    // PERIOD MODE (MONTH / YEAR)
    // -----------------------------
    private val _mode = MutableStateFlow(PeriodMode.MONTH)
    val mode = _mode.asStateFlow()

    // -----------------------------
    // OFFSET (month or year offset)
    // -----------------------------
    private val _selectedOffset = MutableStateFlow(0)
    val selectedOffset = _selectedOffset.asStateFlow()

    // -----------------------------
    // PERIOD LIST ITEMS FOR UI
    // -----------------------------
    private val offsets = mutableListOf(0)
    private val _periodItems = MutableStateFlow<List<PeriodListItem>>(emptyList())
    val periodItems = _periodItems.asStateFlow()

    private val _selectedType = MutableStateFlow(TransactionType.EXPENSE)
    val selectedType = _selectedType.asStateFlow()


    init {
        updateList()
    }

    fun selectTransactionType(type: TransactionType) {
        _selectedType.value = type
    }


    // -----------------------------
    // SWITCH MODES
    // -----------------------------
    fun switchToMonth() {
        _mode.value = PeriodMode.MONTH
        offsets.clear()
        offsets.add(0)
        _selectedOffset.value = 0
        loadMoreMonths()
    }

    fun switchToYear() {
        _mode.value = PeriodMode.YEAR
        offsets.clear()
        offsets.add(0)
        _selectedOffset.value = 0
        loadMoreYears()
    }

    // -----------------------------
    // LOAD MORE MONTHS
    // -----------------------------
    private fun loadMoreMonths() {
        val last = offsets.last()
        for (i in 1..5) offsets.add(last - i)
        updateList()
    }

    // -----------------------------
    // LOAD MORE YEARS
    // -----------------------------
    private fun loadMoreYears() {
        val last = offsets.last()
        for (i in 1..5) offsets.add(last - i)
        updateList()
    }

    fun loadMore() {
        if (_mode.value == PeriodMode.MONTH) loadMoreMonths()
        else loadMoreYears()
    }

    // -----------------------------
    // UPDATE PERIOD LIST FOR UI
    // -----------------------------
    private fun updateList() {
        val now = LocalDate.now()

        val list = offsets.map { offset ->
            val date = when (_mode.value) {
                PeriodMode.MONTH -> now.plusMonths(offset.toLong())
                PeriodMode.YEAR -> now.plusYears(offset.toLong())
            }

            val label = when (_mode.value) {
                PeriodMode.MONTH -> "${monthsNames[date.monthValue - 1]} ${date.year}"
                PeriodMode.YEAR -> date.year.toString()
            }

            PeriodListItem.Period(
                offset = offset,
                label = label,
                year = date.year,
                month = date.monthValue,
                selected = offset == _selectedOffset.value
            )
        } + PeriodListItem.AddMore

        _periodItems.value = list
    }

    fun selectItem(item: PeriodListItem.Period) {
        _selectedOffset.value = item.offset
        updateList()
    }

    // -----------------------------
    // FILTERED TRANSACTIONS
    // -----------------------------
    private val filteredTransactions =
        combine(
            repository.getAllTransactions(),
            categoryRepository.getAllCategories(),
            selectedOffset,
            mode,
            selectedType
        ) { transactions, categories, offset, mode, type ->

            val categoryMap = categories.associateBy { it.id }

            // 1. Filter by date
            val now = LocalDate.now()
            val target = when (mode) {
                PeriodMode.MONTH -> now.plusMonths(offset.toLong())
                PeriodMode.YEAR -> now.plusYears(offset.toLong())
            }

            val dateFiltered = when (mode) {
                PeriodMode.MONTH -> transactions.filter {
                    it.date.year == target.year &&
                            it.date.monthValue == target.monthValue
                }
                PeriodMode.YEAR -> transactions.filter {
                    it.date.year == target.year
                }
            }

            // 2. Filter by EXPENSE / INCOME
            val typeFiltered = dateFiltered.filter { tx ->
                val category = categoryMap[tx.categoryId]
                when (type) {
                    TransactionType.EXPENSE -> category?.type == TypeOfCategory.EXPENSE
                    TransactionType.INCOME -> category?.type == TypeOfCategory.INCOME
                }
            }

            typeFiltered
        }


    // -----------------------------
    // PIE DATA
    // -----------------------------
    val pieData: Flow<List<PieSliceUi>> =
        combine(
            filteredTransactions,
            categoryRepository.getAllCategories(),
            selectedType
        ) { transactions, categories, type ->

            val categoryMap = categories.associateBy { it.id }

            val total = transactions.sumOf { it.amount }.takeIf { it > 0 } ?: 1.0

            when (type) {

                TransactionType.EXPENSE -> {
                    var wants = 0.0
                    var needs = 0.0
                    var savings = 0.0

                    transactions.forEach { tx ->
                        when (categoryMap[tx.categoryId]?.superCategory) {
                            SuperCategory.WANTS -> wants += tx.amount
                            SuperCategory.NEEDS -> needs += tx.amount
                            SuperCategory.SAVINGS -> savings += tx.amount
                            else -> Unit
                        }
                    }

                    listOf(
                        PieSliceUi("Needs", (needs / total).toFloat(), needs, 0xFFDCE775.toInt()),
                        PieSliceUi("Wants", (wants / total).toFloat(), wants, 0xFFF06292.toInt()),
                        PieSliceUi("Savings", (savings / total).toFloat(), savings, 0xFF4DB6AC.toInt())
                    )
                }

                TransactionType.INCOME -> {
                    var salary = 0.0

                    transactions.forEach { tx ->
                        when (categoryMap[tx.categoryId]?.superCategory) {
                            SuperCategory.SALARY -> salary += tx.amount
                            else -> Unit
                        }
                    }

                    listOf(
                        PieSliceUi("Salary", 1f, salary, 0xFF81C784.toInt())
                    )
                }
            }
        }


    // -----------------------------
    // CATEGORY STATISTICS
    // -----------------------------
    val categoryStatisticsData: Flow<List<CategoryStatisticsUi>> =
        combine(
            filteredTransactions,
            categoryRepository.getAllCategories()
        ) { transactions, categories ->

            val categoryMap = categories.associateBy { it.id }

            val total = transactions.sumOf { it.amount }.takeIf { it > 0 } ?: 1.0

            transactions
                .groupBy { it.categoryId }
                .map { (id, list) ->
                    val category = categoryMap[id]!!
                    val sum = list.sumOf { it.amount }

                    CategoryStatisticsUi(
                        categoryName = category.name,
                        categoryIcon = category.icon,
                        amount = sum,
                        percents = (sum / total).toFloat(),
                        color = category.color.toInt()
                    )
                }
                .sortedByDescending { it.amount }
        }

    enum class PeriodMode {
        YEAR, MONTH
    }

    enum class TransactionType {
        EXPENSE, INCOME
    }

}