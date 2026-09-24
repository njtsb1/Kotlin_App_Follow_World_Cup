package me.dio.cup.catar.notification.scheduler.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    companion object {
        const val CHANNEL_ID = "match_notifications"
        const val CHANNEL_NAME = "Match Notifications"
        const val KEY_MATCH_ID = "match_id"
    }

    override fun doWork(): Result {
        val matchId = inputData.getString(KEY_MATCH_ID) ?: return Result.failure()
        showNotification(matchId)
        return Result.success()
    }

    private fun showNotification(matchId: String) {
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            nm.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Match starting soon")
            .setContentText("Your tracked match ($matchId) will start soon.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .build()

        nm.notify(matchId.hashCode(), notification)
    }
}
