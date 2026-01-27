package com.example.pepoasistant.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converter {

    @TypeConverter
    fun fromString(value: String?): LocalDate? =
        value?.let { LocalDate.parse(it) }


    @TypeConverter
    fun localDateToString(date: LocalDate?): String? =
        date?.toString()
}