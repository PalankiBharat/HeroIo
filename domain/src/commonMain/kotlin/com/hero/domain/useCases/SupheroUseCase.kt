package com.hero.domain.useCases

import com.hero.data.repository.SuperheroRepository
import com.hero.data.utils.Response
import com.hero.data.utils.asResult
import com.hero.domain.mapper.toSuperhero
import com.hero.domain.utils.safeApiCall
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class SuperheroUseCase(
    private val superheroRepository: SuperheroRepository
) {

    fun getSuperheroList() = superheroRepository.getSuperheroesFromLocal().map {
        it.map {superheroEntity->
            superheroEntity.toSuperhero()
        }
    }.asResult()

    suspend fun getSuperhero(): Response<Unit?> {
        return safeApiCall {
            superheroRepository.getSuperheroListFromRemote()
        }
    }
}