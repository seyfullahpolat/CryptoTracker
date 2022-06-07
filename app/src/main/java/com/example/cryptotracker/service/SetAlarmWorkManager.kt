/*
package com.example.cryptotracker.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.hilt.work.HiltWorker
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.example.cryptotracker.common.GenerateNotification
import com.example.cryptotracker.db.CryptoRoomDatabase
import com.google.common.util.concurrent.ListenableFuture
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Calendar

*/
/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 *//*


@HiltWorker
class SetAlarmWorkManager @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val db: CryptoRoomDatabase
) : Worker(appContext, workerParams) {
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    override fun doWork(): Result {
        return setAlarm()
    }

    private fun setAlarm(): Result {
        var trackEnableCoin = 0
        coroutineScope.launch {
            db.coinRangeDao().getCoinRangeEnable().collect {
                trackEnableCoin = it.size
                job.complete()
            }
        }

        if (trackEnableCoin == 0) {
            return Result.success()
        }

        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, ScheduleBroadcast::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(appContext, 1999, intent, 0)
        val alarmType = AlarmManager.RTC_WAKEUP

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                val checkIntent = Intent().apply {
                    action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                }
                appContext.startActivity(checkIntent)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                alarmType,
                Calendar.getInstance().timeInMillis + 1000 * 60,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                alarmType,
                Calendar.getInstance().timeInMillis + 1000 * 60,
                pendingIntent
            )
        }

        return Result.success()
    }

    @SuppressLint("RestrictedApi")
    override fun getForegroundInfoAsync(): ListenableFuture<ForegroundInfo> {
        val future = SettableFuture.create<ForegroundInfo>()
        val notificationId = id.hashCode()
        future.set(ForegroundInfo(notificationId, GenerateNotification(appContext).getNotification().build()))
        return future
    }
}
*/
