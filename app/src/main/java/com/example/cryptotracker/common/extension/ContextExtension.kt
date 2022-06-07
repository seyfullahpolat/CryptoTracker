package com.example.cryptotracker.common.extension

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cryptotracker.common.Variables
import com.example.cryptotracker.service.PeriodicTimeFifteenWorkManager
import java.util.concurrent.TimeUnit

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */
fun Context.checkWorkerActive(workName: String) {
    Log.d("CryptoTracker", "$workName.check")
    WorkManager.getInstance(this).cancelAllWorkByTag(workName)
}

fun Context.startTracking() {
    this.checkWorkerActive(Variables.PeriodicTimeFifteenWorkManager)
    this.checkWorkerActive(Variables.SetTimeUnlessFiveWorkManager)

    val compareWorker =
        PeriodicWorkRequestBuilder<PeriodicTimeFifteenWorkManager>(
            1000,
            TimeUnit.MILLISECONDS
        )
            .addTag(Variables.PeriodicTimeFifteenWorkManager)
            .build()
    WorkManager.getInstance(this).enqueue(compareWorker)
}
