package uz.gita.newdrinkwater.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.gita.newdrinkwater.R


class DrinkWaterManager(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val CHANNELID = "CHANNELID"

    override fun doWork(): Result {
        return try {
            setNotification(1, "Drink Water", "You need to drink water")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun setNotification(id: Int, title: String, description: String) {

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNELID, "Work Manager", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val build = NotificationCompat.Builder(applicationContext, CHANNELID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.drop)

        manager.notify(id, build.build())
    }

}