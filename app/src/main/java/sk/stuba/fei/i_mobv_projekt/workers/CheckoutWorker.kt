package sk.stuba.fei.i_mobv_projekt.workers

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.data.api.BarMessageRequest
import sk.stuba.fei.i_mobv_projekt.data.api.RestApi

class CheckoutWorker(val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            1, createNotification()
        )
    }

    override suspend fun doWork(): Result {
        val response =
            RestApi.create(appContext).barMessage(BarMessageRequest("", "", "", 0.0, 0.0))
        return if (response.isSuccessful) Result.success() else Result.failure()
    }

    private fun createNotification(): Notification {
        val builder =
            NotificationCompat.Builder(appContext, "mobv2022").apply {
                setContentTitle("MOBV 2022")
                setContentText("Exiting bar ...")
                setSmallIcon(R.drawable.ic_baseline_location_on_24)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }

        return builder.build()
    }


}