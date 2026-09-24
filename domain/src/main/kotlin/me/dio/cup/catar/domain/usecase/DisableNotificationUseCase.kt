package me.dio.cup.catar.domain.usecase

import android.content.Context
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DisableNotificationUseCase @Inject constructor(
    private val context: Context
) {
    suspend operator fun invoke(matchId: String) = withContext(Dispatchers.IO) {
        val workName = "match_notification_unique_$matchId"
        WorkManager.getInstance(context).cancelUniqueWork(workName)
        WorkManager.getInstance(context).cancelAllWorkByTag("match_notification_$matchId")
    }
}
