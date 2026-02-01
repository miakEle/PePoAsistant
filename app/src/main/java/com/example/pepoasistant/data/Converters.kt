package com.example.pepoasistant.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun localDateToLong(date: LocalDate?): Long? =
        date?.toEpochDay()

    @TypeConverter
    fun longToLocalDate(value: Long?): LocalDate? =
        value?.let { LocalDate.ofEpochDay(it) }


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
