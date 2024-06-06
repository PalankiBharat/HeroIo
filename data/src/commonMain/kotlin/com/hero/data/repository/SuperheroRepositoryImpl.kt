package com.hero.data.repository

import com.hero.data.local.SuperheroDao
import com.hero.data.model.SuperheroEntity
import com.hero.data.remote.api.SuperheroApi
import com.hero.data.utils.toSuperheroEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single

@Single(binds = [SuperheroRepository::class])
class SuperheroRepositoryImpl(
    private val api: SuperheroApi,
    private val superheroDao: SuperheroDao
) : SuperheroRepository {
    override suspend fun getSuperheroListFromRemote() {
        val superheroList = api.getSuperHeroList()
        if (superheroList.isNotEmpty()) {
            replaceOldData(superheroes = superheroList.map {
                item -> item?.toSuperheroEntity() })
        }
    }

    override fun getSuperheroesFromLocal(): Flow<List<SuperheroEntity>> =
        superheroDao.getAllHeroes()

    private suspend fun replaceOldData(superheroes: List<SuperheroEntity?>) {
        runBlocking {
            superheroDao.deleteAllHeroes()
            superheroDao.addAllHeroes(superheroes.requireNoNulls())
        }
    }
}