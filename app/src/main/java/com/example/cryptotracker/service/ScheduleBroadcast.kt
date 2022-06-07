/*
package com.example.cryptotracker.service

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScheduleBroadcast : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        when (intent.action) {
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                // reschedule all the exact alarms
            }
        }

        */
/** context.checkWorkerActive(Variables.CHECK_RATE_ON_RANGE)
 context.checkWorkerActive(Variables.SET_ALARM_WORK_MANAGER)

 val compareWorker = OneTimeWorkRequestBuilder<CheckRateOnRangeWorker>()
 .addTag(Variables.CHECK_RATE_ON_RANGE)
 .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
 .build()

 val setAlarmWorkManager = OneTimeWorkRequestBuilder<SetAlarmWorkManager>()
 .addTag(Variables.SET_ALARM_WORK_MANAGER)
 .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
 .build()
 WorkManager.getInstance(context).enqueue(listOf(setAlarmWorkManager, compareWorker))*//*

    }
}
*/
