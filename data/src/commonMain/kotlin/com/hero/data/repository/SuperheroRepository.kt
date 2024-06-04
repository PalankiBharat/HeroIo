package com.hero.data.repository

import com.hero.data.model.SuperheroEntity
import kotlinx.coroutines.flow.Flow

interface SuperheroRepository {
    fun getSuperheroesFromLocal(): Flow<List<SuperheroEntity>>
    suspend fun getSuperheroListFromRemote()
}