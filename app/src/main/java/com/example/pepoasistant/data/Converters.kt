package com.example.pepoasistant.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory
import java.time.LocalDate

class Converters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromString(value: String?): LocalDate? =
        value?.let { LocalDate.parse(it) }


    @TypeConverter
    fun localDateToString(date: LocalDate?): String? =
        date?.toString()

    @TypeConverter
    fun fromEnum(value: SuperCategory): String = value.name

    @TypeConverter
    fun toEnum(value: String): SuperCategory =
        SuperCategory.valueOf(value)

    @TypeConverter
    fun fromTypeOfCategory(value: TypeOfCategory): String = value.name

    @TypeConverter
    fun toTypeOfCategory(value: String): TypeOfCategory =
        TypeOfCategory.valueOf(value)


}
