package sk.stuba.fei.i_mobv_projekt.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.stuba.fei.i_mobv_projekt.api.Api
import sk.stuba.fei.i_mobv_projekt.api.PubRequest
import sk.stuba.fei.i_mobv_projekt.database.PubDatabase
import sk.stuba.fei.i_mobv_projekt.database.asDomainModel
import sk.stuba.fei.i_mobv_projekt.parser.PubExtension
import sk.stuba.fei.i_mobv_projekt.parser.asDatabaseModel

class ItemRepository(private val database: PubDatabase) {
    val list: LiveData<PubExtension> = Transformations.map(database.pubDao().getItems())
    {
        it.asDomainModel()
    }

    suspend fun refresh() {
        try {
            withContext(Dispatchers.IO) {
                val data = Api.retrofitService.getJsonString(
                    PubRequest(
                        collection = "bars",
                        database = "mobvapp",
                        dataSource = "Cluster0"
                    )
                )
                database.pubDao().insertAll(data.asDatabaseModel())
            }
        }
        catch (e: Exception) {
            Log.e("ItemRepository", e.message.toString())
        }
    }
}