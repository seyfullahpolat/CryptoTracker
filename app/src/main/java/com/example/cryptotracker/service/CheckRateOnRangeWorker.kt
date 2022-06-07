package com.example.cryptotracker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.cryptotracker.R
import com.example.cryptotracker.api.Error
import com.example.cryptotracker.api.Success
import com.example.cryptotracker.common.GenerateNotification
import com.example.cryptotracker.common.Variables
import com.example.cryptotracker.common.Variables.EMPTY
import com.example.cryptotracker.common.Variables.NOTIFICATION_CHANNEL_ID
import com.example.cryptotracker.common.Variables.NOTIFICATION_CHANNEL_NAME
import com.example.cryptotracker.common.extension.toCurrency
import com.example.cryptotracker.db.CoinHistoryItem
import com.example.cryptotracker.db.CryptoRoomDatabase
import com.example.cryptotracker.feature.main.MainActivity
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinCurrency
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem
import com.example.cryptotracker.feature.main.coinlist.domain.interactor.CoinListUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltWorker
class CheckRateOnRangeWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val coinListUseCase: CoinListUseCase,
    private val db: CryptoRoomDatabase,
) : CoroutineWorker(appContext, workerParams) {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    override suspend fun doWork(): Result = try {
        writeDbRange()
        job.join()
        Result.success()
    } catch (e: Exception) {
        Result.failure(Data.Builder().putString("CheckRateOnRangeWorker", e.message).build())
    }

    private fun writeDbRange() = coroutineScope.launch {
        db.coinRangeDao().getCoinRangeEnable().transform { list ->
            if (list.isNotEmpty()) {
                val param = CoinListUseCase.Params(
                    ids = list.joinToString(separator = ",") { it.cointId },
                    vs_currencies = Variables.CURRENT_CURRENCY
                )
                emitAll(coinListUseCase.execute(param))
            } else {
                WorkManager.getInstance(appContext).cancelAllWork()
                job.cancel()
            }
        }.collect { dataState ->
            when (dataState) {
                is Success -> {
                    dataState.data?.apply {
                        val response = dataState.data.map { data ->
                            parseResponseToCryptoItem(data)
                        }
                        val message = generateMessage(response).joinToString(separator = "\n")
                        if (message.replace("\n", EMPTY).isNotEmpty()) {
                            setNotification(message)
                        }
                    }
                    job.cancel()
                }
                is Error -> {
                    job.cancel()
                }
                else -> {}
            }
        }
    }

    private fun parseResponseToCryptoItem(data: Map.Entry<String, HashMap<String, String>>) =
        CoinItem(
            data.key,
            data.value.map {
                CoinCurrency(
                    it.key, it.value
                )
            }.first()
        )

    private fun generateMessage(response: List<CoinItem>) = response.map { cryptoItem ->
        val result = db.coinRangeDao().getRateOutRangeCoinById(
            cryptoItem.coinCurrency.rates.toFloat(),
            cryptoItem.name
        )
        result?.let {
            saveHistory(cryptoItem)
            cryptoItem.name.replaceFirstChar { it.uppercaseChar() } + Variables.SPACE_CHAR + cryptoItem.coinCurrency.rates.toFloat()
                .toCurrency()
        } ?: EMPTY
    }

    private fun setNotification(message: String) {
        val notificationMessage =
            message + appContext.getString(R.string.notification_body_extension)
        GenerateNotification(context = appContext).getNotification().apply {
            setContentText(notificationMessage)
            setStyle(
                NotificationCompat.BigTextStyle().bigText(notificationMessage)
            )

            val notificationManager = NotificationManagerCompat.from(appContext)
            notificationManager.notify(Random.nextInt(0, 1000), build())
        }
    }

    private fun saveHistory(coinItem: CoinItem) {
        db.coinHistoryDao().addHistory(
            CoinHistoryItem(
                coinId = coinItem.name,
                coinPrice = coinItem.coinCurrency.rates + Variables.CURRENT_CURRENCY
            )
        )
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(
                PendingIntent.getActivity(
                    appContext,
                    0,
                    Intent(appContext, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(appContext.getString(R.string.app_name))
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setContentText(appContext.getString(R.string.training_message))
            .build()
        return ForegroundInfo(1337, notification)
    }
}
