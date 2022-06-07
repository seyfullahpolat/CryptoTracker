package com.example.cryptotracker.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.example.cryptotracker.R
import com.example.cryptotracker.common.Variables
import com.example.cryptotracker.common.extension.checkWorkerActive
import com.google.common.util.concurrent.ListenableFuture
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltWorker
class SetTimeUnlessFiveWorkManager @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        appContext.checkWorkerActive(Variables.CHECK_RATE_ON_RANGE)

        val compareWorker = OneTimeWorkRequestBuilder<CheckRateOnRangeWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .addTag(Variables.CHECK_RATE_ON_RANGE)
            .build()

        WorkManager.getInstance(appContext).enqueue(compareWorker)
        Log.d("cananım", "sdfdsfddfs")
        return Result.retry()
    }

    @SuppressLint("RestrictedApi")
    override fun getForegroundInfoAsync(): ListenableFuture<ForegroundInfo> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = appContext.getString(R.string.channel_name)
            val descriptionText = appContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(appContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(alarmSound)
            .setContentTitle(appContext.getString(R.string.app_name))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val future = SettableFuture.create<ForegroundInfo>()
        val notificationId = id.hashCode()
        future.set(ForegroundInfo(notificationId, notification.build()))
        return future
    }
}
