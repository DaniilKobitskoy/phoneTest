package com.mysticism.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mysticism.data.models.Data

@Database(entities = [Data::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
