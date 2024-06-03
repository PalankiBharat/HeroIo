package com.hero.domain.mapper

import com.hero.data.model.PowerStatsNetworkEnitity
import com.hero.domain.model.PowerStats

fun PowerStatsNetworkEnitity.toPowerStats() : PowerStats = PowerStats(
    strength = strength,
    durability = durability,
    combat = combat,
    power = power,
    speed = speed,
    intelligence = intelligence
)