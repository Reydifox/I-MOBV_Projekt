package sk.stuba.fei.i_mobv_projekt.helpers

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import sk.stuba.fei.i_mobv_projekt.data.DataRepository
import sk.stuba.fei.i_mobv_projekt.data.api.RestApi
import sk.stuba.fei.i_mobv_projekt.data.db.AppRoomDatabase
import sk.stuba.fei.i_mobv_projekt.data.db.LocalCache

object Injection {
    private fun provideCache(context: Context): LocalCache {
        val database = AppRoomDatabase.getInstance(context)
        return LocalCache(database.appDao())
    }

    fun provideDataRepository(context: Context): DataRepository {
        return DataRepository.getInstance(RestApi.create(context), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(
            provideDataRepository(
                context
            )
        )
    }
}