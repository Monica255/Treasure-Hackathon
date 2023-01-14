package com.example.treasurehackathon.core.di

import android.content.Context
import androidx.room.Room
import com.example.treasurehackathon.core.data.source.local.room.Dao
import com.example.treasurehackathon.core.data.source.local.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java, "Hackathon.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: Database): Dao = database.dao()
}