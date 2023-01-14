package com.example.treasurehackathon.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.treasurehackathon.core.data.source.local.model.AccountEntity
import com.example.treasurehackathon.core.data.source.local.model.ProductEntity

@Database(entities = [AccountEntity::class,ProductEntity::class], version = 2, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}