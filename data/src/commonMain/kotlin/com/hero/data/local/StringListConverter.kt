package com.hero.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.hero.data.model.ImagesNetworkEntity
import com.hero.data.model.PowerStatsNetworkEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String?> {
        return value.split(":").map { it.trim() }
    }

    @TypeConverter
    fun toList(list: List<String?>): String {
        return list.joinToString(":")
    }
}

class PowerStatsConverter {
    @TypeConverter
    fun fromPowerStats(value: PowerStatsNetworkEntity): String {
        return Json.encodeToJsonElement(value).toString()
    }

    @TypeConverter
    fun toPowerStats(json: String): PowerStatsNetworkEntity {
        return Json.decodeFromString(json)
    }
}

@ProvidedTypeConverter
class ImagesConverter {
    @TypeConverter
    fun fromImages(value: ImagesNetworkEntity): String {
        return Json.encodeToJsonElement(value).toString()
    }

    @TypeConverter
    fun toImages(json: String): ImagesNetworkEntity {
        return Json.decodeFromString(json)
    }
}