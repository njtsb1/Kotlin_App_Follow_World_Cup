package me.dio.cup.catar.notification.scheduler.extensions

import android.content.Context
import androidx.work.*
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit

object NotificationScheduler {
    suspend fun scheduleMatchNotification(context: Context, matchId: String, matchInstant: Instant, minutesBefore: Long = 30) {
        val delayMillis = Duration.between(Instant.now(), matchInstant).toMillis().coerceAtLeast(0L)
        val initialDelay = (delayMillis - TimeUnit.MINUTES.toMillis(minutesBefore)).coerceAtLeast(0L)

        val data = Data.Builder()
            .putString(NotificationWorker.KEY_MATCH_ID, matchId)
            .build()

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("match_notification_$matchId")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "match_notification_unique_$matchId",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    suspend fun cancelMatchNotification(context: Context, matchId: String) {
        WorkManager.getInstance(context).cancelUniqueWork("match_notification_unique_$matchId")
        WorkManager.getInstance(context).cancelAllWorkByTag("match_notification_$matchId")
    }
}
