package com.example.treasurehackathon

import com.example.treasurehackathon.core.data.Repository
import com.example.treasurehackathon.core.domain.usercase.Interactor
import com.example.treasurehackathon.core.domain.usercase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUseCase(repository: Repository): UseCase=Interactor(repository)

}