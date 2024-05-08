package data.local

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String?> {
        return value.split(":").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String?>): String {
        return list.joinToString(":")
    }
}