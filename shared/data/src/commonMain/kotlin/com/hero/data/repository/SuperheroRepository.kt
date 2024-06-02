package com.hero.data.repository

import com.hero.data.model.SuperheroEntity
import com.hero.data.utils.Response
import kotlinx.coroutines.flow.Flow

interface SuperheroRepository {
    fun getSuperheroesFromLocal(): Flow<List<SuperheroEntity?>>
    suspend fun getSuperheroListFromRemote()
}