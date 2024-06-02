package com.hero.domain.mapper

import com.hero.data.model.ImagesEntity
import com.hero.domain.model.Images

fun ImagesEntity.toImages(): Images = Images(
    smallImage = sm,
    midImage = md,
    largeImage = lg
)