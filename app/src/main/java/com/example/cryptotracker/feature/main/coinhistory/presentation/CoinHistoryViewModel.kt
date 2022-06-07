package com.example.cryptotracker.feature.main.coinhistory.presentation

import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.base.view.BaseViewModel
import com.example.cryptotracker.feature.main.coinhistory.domain.interactor.CoinHistoryLocalData
import com.example.cryptotracker.feature.main.coinhistory.domain.mapper.toViewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltViewModel
class CoinHistoryViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val coinHistoryLocalData: CoinHistoryLocalData
) : BaseViewModel() {

    lateinit var coinId: String

    @ExperimentalCoroutinesApi
    fun getHistory() {
        viewModelScope.launch(dispatcher) {
            getAllHistoryById(coinId).collect {
                _liveData.value = it.toViewEntity()
            }
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllHistoryById(id: String) = coinHistoryLocalData.getAllHistoryById(id)
}
