package com.hero.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hero.data.model.SuperheroChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperheroChatDao {

    @Query("SELECT * FROM SuperheroChatEntity WHERE superheroID = :id")
    fun getAllChatsBySuperhero(id: String): Flow<List<SuperheroChatEntity>>

    @Insert
    suspend fun addChat(chatEntity: SuperheroChatEntity)

    @Query("DELETE FROM SuperheroChatEntity WHERE superheroID = :id")
    suspend fun deleteAllChatOfSSuperhero(id: String)
}