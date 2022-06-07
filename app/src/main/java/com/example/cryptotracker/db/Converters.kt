package com.example.cryptotracker.db

import androidx.room.TypeConverter
import com.example.cryptotracker.common.extension.toRoundFourDigit
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun floatToString(value: Float?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun stringToFloat(value: String?): Float? {
        return value?.toFloat()?.toRoundFourDigit()
    }
}
