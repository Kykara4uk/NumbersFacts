package com.example.numbersfacts.di

import android.content.Context
import com.example.numbersfacts.data.local.NumbersDatabase
import com.example.numbersfacts.data.remote.api.NumbersClient
import com.example.numbersfacts.data.remote.api.NumbersInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides @Singleton
    fun provideNumbersApiService() : NumbersInterface = NumbersClient.getClient()

    @Provides @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context):NumbersDatabase = NumbersDatabase.invoke(context)
}