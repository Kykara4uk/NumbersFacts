package com.example.numbersfacts.di

import com.example.numbersfacts.data.repository.NumbersRepositoryImpl
import com.example.numbersfacts.domain.repository.NumbersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module @InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds @Singleton
    abstract fun bindNumbersRepository(numbersRepositoryImpl: NumbersRepositoryImpl) : NumbersRepository


}