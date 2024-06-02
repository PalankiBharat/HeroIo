package com.hero.domain.useCases

import com.hero.data.repository.SuperheroRepository
import com.hero.data.utils.asResult
import com.hero.domain.mapper.toSuperhero
import kotlinx.coroutines.flow.map

class SuperheroUseCase(
    private val superheroRepository: SuperheroRepository
) {

    fun getSuperheroList() = superheroRepository.getSuperheroesFromLocal().map {
        it.map {superheroEntity->
            superheroEntity?.toSuperhero()
        }
    }.asResult()

    suspend fun getSuperhero() = superheroRepository.getSuperheroListFromRemote()
}