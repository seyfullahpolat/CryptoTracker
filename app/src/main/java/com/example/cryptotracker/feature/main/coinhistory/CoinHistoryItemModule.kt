package com.example.cryptotracker.feature.main.coinhistory

import com.example.cryptotracker.db.CryptoRoomDatabase
import com.example.cryptotracker.feature.main.coinhistory.data.repository.CoinHistoryRepository
import com.example.cryptotracker.feature.main.coinhistory.data.repository.CoinHistoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object CoinHistoryItemModule {

    @Provides
    @Singleton
    fun provideCoinRepository(db: CryptoRoomDatabase): CoinHistoryRepository {
        return CoinHistoryRepositoryImpl(db)
    }
}
