package com.hero.domain.mapper

import com.hero.data.model.ImagesNetworkEntity
import com.hero.domain.model.Images

fun ImagesNetworkEntity.toImages(): Images = Images(
    smallImage = sm,
    midImage = md,
    largeImage = lg
)