package com.example.cryptotracker.feature.main.coinlist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.work.WorkManager
import com.example.cryptotracker.MainCoroutineRule
import com.example.cryptotracker.api.DataState
import com.example.cryptotracker.api.Error
import com.example.cryptotracker.api.Success
import com.example.cryptotracker.common.extension.getOrAwaitValue
import com.example.cryptotracker.feature.main.coinlist.domain.interactor.CoinListUseCase
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by Seyfullah POLAT on 7.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CoinListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CoinListViewModel

    @Mock
    private lateinit var coinListUseCase: CoinListUseCase

    @Mock
    private lateinit var workManager: WorkManager

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = CoinListViewModel(
            mainCoroutineRule.dispatcher,
            coinListUseCase,
            workManager
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetCoinList() = runTest {
        // Given
        val channel = Channel<DataState<HashMap<String, HashMap<String, String>>>>()
        val flow = channel.consumeAsFlow()
        val pair = Pair("usd", "30000")
        val coinCurrency = hashMapOf(pair)
        val hashData = hashMapOf<String, HashMap<String, String>>()
        hashData["bitcoin"] = coinCurrency
        val data: HashMap<String, HashMap<String, String>> = hashData

        Mockito.`when`(coinListUseCase.execute(any())).thenReturn(flow)
        launch(this.coroutineContext) {
            channel.send(Success(data))
        }

        // when
        viewModel.getCoinList()
        delay(10)
        val result = viewModel.responseList

        // Then
        Assert.assertTrue(result.isNotEmpty())
        Assert.assertTrue(result.first().name == data.keys.first())
    }

    @Test
    fun testGetCoinListWithHttpError404() = runTest {
        // Given
        val errorResponse =
            "{\n" +
                "  \"type\": \"error\",\n" +
                "  \"message\": \"What you were looking for isn't here.\"\n" +
                "}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val mockResponse = Response.error<String>(404, errorResponseBody)

        val error = HttpException(mockResponse)

        val channel = Channel<DataState<HashMap<String, HashMap<String, String>>>>()
        val flow = channel.consumeAsFlow()

        Mockito.`when`(coinListUseCase.execute(any())).thenReturn(flow)
        launch(this.coroutineContext) {
            channel.send(Error(error))
        }
        // when
        viewModel.getCoinList()
        delay(10)
        val result = viewModel.loading.getOrAwaitValue() as Error
        // Then
        Assert.assertTrue(result.error is HttpException)
        Assert.assertTrue((result.error as HttpException).code() == 404)
    }
}
