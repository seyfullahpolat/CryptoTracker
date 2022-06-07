package com.example.cryptotracker.feature.main.coinlist

import com.example.cryptotracker.api.ApiServices
import com.example.cryptotracker.db.CryptoRoomDatabase
import com.example.cryptotracker.feature.main.coinlist.data.repository.CoinListRepository
import com.example.cryptotracker.feature.main.coinlist.data.repository.CoinListRepositoryImpl
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
object CoinListModule {

    @Provides
    @Singleton
    fun provideCoinListRepository(
        api: ApiServices,
        db: CryptoRoomDatabase
    ): CoinListRepository {
        return CoinListRepositoryImpl(api, db)
    }
}
