package com.mysticism.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mysticism.data.models.Data

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<Data>

    @Query("SELECT * FROM contacts WHERE is_favorite = 1")
    suspend fun getFavoriteContacts(): List<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<Data>)

    @Query("UPDATE contacts SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM contacts WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): Data?

    @Query("UPDATE contacts SET image_path = :imagePath WHERE id = :id")
    suspend fun updateContactImage(id: Int, imagePath: String)
}

