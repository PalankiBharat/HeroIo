package com.hero.data.remote.api

import com.hero.data.model.SuperheroNetworkEntity

interface SuperheroApiService {
    suspend fun getSuperHeroList():List<SuperheroNetworkEntity?>
    suspend fun getSuperHeroById(id:Int): SuperheroNetworkEntity?
}