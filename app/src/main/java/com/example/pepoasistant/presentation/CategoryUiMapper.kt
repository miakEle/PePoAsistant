package com.example.pepoasistant.presentation

import com.example.pepoasistant.domain.entities.Category
import android.content.Context

class CategoryUiMapper(private val context: Context) {

    fun map(category: Category): CategoryUi {
        val resId = context.resources.getIdentifier(
            category.icon,
            "drawable",
            context.packageName
        )

        return CategoryUi(
            id = category.id,
            name = category.name,
            icon = resId
        )
    }

}