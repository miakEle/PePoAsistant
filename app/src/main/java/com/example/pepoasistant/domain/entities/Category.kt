package com.example.pepoasistant.domain.entities

enum class SuperCategory{
    NEEDS, WANTS, SAVINGS
}

data class Category (
    val id: Long,
    val name:String,
    val icon: String,
    val color: Long,
    val superCategory: SuperCategory
){
}