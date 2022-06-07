package com.example.cryptotracker.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cryptotracker.common.Variables
import com.example.cryptotracker.common.extension.checkWorkerActive
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltWorker
class PeriodicTimeFifteenWorkManager @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        appContext.checkWorkerActive(Variables.SetTimeUnlessFiveWorkManager)
        val compareWorker =
            OneTimeWorkRequestBuilder<SetTimeUnlessFiveWorkManager>()
                .addTag(Variables.SetTimeUnlessFiveWorkManager)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    60 * 1000,
                    TimeUnit.MILLISECONDS
                )
                .build()
        WorkManager.getInstance(appContext).enqueue(compareWorker)
        return Result.success()
    }
}
