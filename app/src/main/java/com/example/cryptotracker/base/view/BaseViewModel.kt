package com.example.cryptotracker.base.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptotracker.api.DataState
import com.example.cryptotracker.base.data.model.BaseViewEntity

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

abstract class BaseViewModel : ViewModel() {

    val liveData: LiveData<BaseViewEntity>
        get() = _liveData
    internal val _liveData: MutableLiveData<BaseViewEntity> = MutableLiveData()

    val loading: MutableLiveData<DataState<*>> = MutableLiveData()
}
