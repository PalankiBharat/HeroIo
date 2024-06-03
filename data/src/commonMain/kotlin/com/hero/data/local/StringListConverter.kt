package com.hero.data.local

import androidx.room.TypeConverter
import com.hero.data.model.ImagesEntity
import com.hero.data.model.PowerStatsNetworkEnitity
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
    fun fromPowerStats(value: PowerStatsNetworkEnitity): String {
        return Json.encodeToJsonElement(value).toString()
    }

    @TypeConverter
    fun toPowerStats(json: String): PowerStatsNetworkEnitity {
        return Json.decodeFromString(json)
    }
}

class ImagesConverter {
    @TypeConverter
    fun fromImages(value: ImagesEntity): String {
        return Json.encodeToJsonElement(value).toString()
    }

    @TypeConverter
    fun toImages(json: String): ImagesEntity {
        return Json.decodeFromString(json)
    }
}