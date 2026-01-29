package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory

object CategoryPrepopulateData {

    val defaultCategories = listOf<Category>(
        Category(
            name = "Ruoka",
            icon = "ic_food",
            color = 0xFF2196F3,
            superCategory = SuperCategory.NEEDS,
            type = TypeOfCategory.EXPENSE
        ),

        Category(
            name = "Lainat",
            icon = "ic_credit",
            color = 0xFF2196F3,
            superCategory = SuperCategory.NEEDS,
            type = TypeOfCategory.EXPENSE
        ),
        Category(
            name = "Palkka",
            icon = "ic_money_bag",
            color = 0xFF2196F3,
            superCategory = SuperCategory.SALARY,
            type = TypeOfCategory.INCOME
        )

    )
}