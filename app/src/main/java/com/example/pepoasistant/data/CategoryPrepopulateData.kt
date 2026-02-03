package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory

object CategoryPrepopulateData {

    val defaultCategories = listOf<Category>(

        Category(
            name = "Säästöjä",
            icon = "ic_money_bag",
            color = 0xFFE57373,
            superCategory = SuperCategory.SAVINGS,
            type = TypeOfCategory.EXPENSE
        ),
        Category(
            name = "Ruoka",
            icon = "ic_food",
            color = 0xFFE57373,
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
            name = "Auto",
            icon = "ic_car",
            color = 0xFFFFB74D,
            superCategory = SuperCategory.NEEDS,
            type = TypeOfCategory.EXPENSE
        ),

        Category(
            name = "Lapset",
            icon = "ic_child",
            color = 0xFF4CAF50,
            superCategory = SuperCategory.NEEDS,
            type = TypeOfCategory.EXPENSE
        ),

        Category(
            name = "Vaatteet",
            icon = "ic_clothes",
            color = 0xFFFFEB3B,
            superCategory = SuperCategory.WANTS,
            type = TypeOfCategory.EXPENSE
        ),

        Category(
            name = "Viihde",
            icon = "ic_fun",
            color = 0xFF9C27B0,
            superCategory = SuperCategory.WANTS,
            type = TypeOfCategory.EXPENSE
        ),

        Category(
            name = "Asuminen",
            icon = "ic_house",
            color = 0xFF2196F3,
            superCategory = SuperCategory.NEEDS,
            type = TypeOfCategory.EXPENSE
        ),
        Category(
            name = "Puhelin",
            icon = "ic_phone",
            color = 0xFFF06292,
            superCategory = SuperCategory.WANTS,
            type = TypeOfCategory.EXPENSE
        ),

        Category(
            name = "Koulutus",
            icon = "ic_study",
            color = 0xFFDCE775,
            superCategory = SuperCategory.WANTS,
            type = TypeOfCategory.EXPENSE
        ),
        Category(
            name = "Palkka",
            icon = "ic_money_bag",
            color = 0xFF4DD0E1,
            superCategory = SuperCategory.SALARY,
            type = TypeOfCategory.INCOME
        )

    )
}