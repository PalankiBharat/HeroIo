package com.hero.data.remote.api

import com.hero.data.model.Superhero

interface SuperheroApiService {
    suspend fun getSuperHeroList():List<Superhero?>
    suspend fun getSuperHeroById(id:Int): Superhero?
}