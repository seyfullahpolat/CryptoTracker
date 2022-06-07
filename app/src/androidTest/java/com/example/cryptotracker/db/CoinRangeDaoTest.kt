package com.example.cryptotracker.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.example.cryptotracker.common.extension.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Seyfullah POLAT on 7.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltAndroidTest
@SmallTest
class CoinRangeDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("productList")
    lateinit var database: CryptoRoomDatabase
    private lateinit var coinRangeDao: CoinRangeDao

    @Before
    fun setup() {
        hiltRule.inject()
        coinRangeDao = database.coinRangeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlocking {
        val coinRangeItem = CoinRangeItem(
            cointId = "bitcoin",
            minRate = 20000F,
            maxRate = 40000F,
            enable = true
        )
        coinRangeDao.updateCoinRange(coinRangeItem)
        val allUsers = coinRangeDao.getCoinRangeEnable().asLiveData().getOrAwaitValue()
        assertTrue(allUsers.contains(coinRangeItem))
    }
}
