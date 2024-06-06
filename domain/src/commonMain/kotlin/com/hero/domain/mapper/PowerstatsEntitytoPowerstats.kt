package com.hero.domain.mapper

import com.hero.data.model.PowerStatsNetworkEntity
import com.hero.domain.model.PowerStats

fun PowerStatsNetworkEntity.toPowerStats() : PowerStats = PowerStats(
    strength = strength,
    durability = durability,
    combat = combat,
    power = power,
    speed = speed,
    intelligence = intelligence
)