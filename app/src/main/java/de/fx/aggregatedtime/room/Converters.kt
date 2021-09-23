package de.fx.aggregatedtime.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.let { DateTimeFormatter.ISO_LOCAL_DATE.format(value) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toUuID(value: String?): UUID? {
        return value?.let { UUID.fromString(value) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromUuid(value: UUID?): String? {
        return value?.let { value.toString() }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toProjectState(value: String?): ProjectState? {
        return value?.let { ProjectState.valueOf(value) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromProjectState(value: ProjectState?): String? {
        return value?.let { value.name }
    }
}