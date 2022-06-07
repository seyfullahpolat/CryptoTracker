package com.example.cryptotracker.di

import android.content.Context
import androidx.work.WorkManager
import com.example.cryptotracker.CryptoTrackerApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) = app as CryptoTrackerApp

    @Singleton
    @Provides
    fun provideContext(application: CryptoTrackerApp): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext appContext: Context): WorkManager =
        WorkManager.getInstance(appContext)

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
