package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Category

fun CategoryEntity.toDomain(): Category =
    Category(
        id = id,
        name = name,
        icon = icon,
        color = color,
        superCategory = superCategory,
        type = type
    )

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        id = id,
        name = name,
        icon = icon,
        color = color,
        superCategory = superCategory,
        type = type
    )

fun List<Category>.toEntityList(): List<CategoryEntity> =
    this.map { it.toEntity() }



