package me.dio.cup.catar.domain.usecase

import android.content.Context
import androidx.work.*
import me.dio.cup.catar.notification.NotificationWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EnableNotificationUseCase @Inject constructor(
    private val context: Context
) {
    suspend operator fun invoke(matchId: String, matchInstant: Instant) = withContext(Dispatchers.IO) {
        val delayMillis = Duration.between(Instant.now(), matchInstant).toMillis().coerceAtLeast(0L)
        // Example: notify 30 minutes before match
        val notifyBeforeMillis = TimeUnit.MINUTES.toMillis(30)
        val initialDelay = (delayMillis - notifyBeforeMillis).coerceAtLeast(0L)

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
}
