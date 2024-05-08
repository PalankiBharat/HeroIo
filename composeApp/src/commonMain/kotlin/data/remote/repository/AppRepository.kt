package data.remote.repository

import data.local.SuperheroDao
import data.model.SuperheroEntity
import data.remote.api.SuperheroApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import utils.toSuperheroEntity

class AppRepository(private val api: SuperheroApi, private val superheroDao: SuperheroDao) {
    suspend fun getSuperheroList() {
        val superheroList = api.getSuperHeroList()
        if (superheroList.isNotEmpty()) {
            replaceOldData(superheroes = superheroList.map { item -> item?.toSuperheroEntity() })
        }
    }

     fun getSuperheroesFromLocal(): Flow<List<SuperheroEntity?>> = superheroDao.getAllHeroes()


    private suspend fun replaceOldData(superheroes: List<SuperheroEntity?>) {
        try {
            runBlocking {
                superheroDao.deleteAllHeroes()
                superheroDao.addAllHeroes(superheroes.requireNoNulls())
            }
        } catch (e: Exception) {
            println(e.message.toString() + "getAllHeroes")
        }
    }


}