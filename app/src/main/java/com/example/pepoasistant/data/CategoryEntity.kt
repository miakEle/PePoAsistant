package com.example.pepoasistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String,
    val color: Long,
    val superCategory: SuperCategory,
    val type: TypeOfCategory

)