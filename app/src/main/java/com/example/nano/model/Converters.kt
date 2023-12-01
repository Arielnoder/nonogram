package com.example.nano.model

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromBooleanList(value: List<Boolean>): String {
        return value.joinToString(separator = ",") { it.toString() }
    }

    @TypeConverter
    fun toBooleanList(value: String): List<Boolean> {
        return value.split(",").map { it.toBoolean() }
    }
}
