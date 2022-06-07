package com.example.cryptotracker.feature.main

import androidx.work.WorkManager
import com.example.cryptotracker.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    val workManager: WorkManager
) : BaseViewModel()
