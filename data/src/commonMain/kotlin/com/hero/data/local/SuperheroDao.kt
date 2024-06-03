package com.hero.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hero.data.model.SuperheroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperheroDao {

    @Query("SELECT * FROM SuperheroEntity")
     fun getAllHeroes(): Flow<List<SuperheroEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllHeroes(heroes: List<SuperheroEntity>)

    @Query("SELECT * FROM SuperheroEntity WHERE id = :id")
    suspend fun getHeroById(id: String): SuperheroEntity?

    @Query("DELETE FROM SuperheroEntity")
    suspend fun deleteAllHeroes()
}