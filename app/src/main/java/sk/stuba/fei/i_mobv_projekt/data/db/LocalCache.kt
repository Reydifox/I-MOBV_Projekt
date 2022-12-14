package sk.stuba.fei.i_mobv_projekt.data.db

import androidx.lifecycle.LiveData
import sk.stuba.fei.i_mobv_projekt.data.db.model.BarItem
import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem

class LocalCache(private val dao: DbDao) {
    suspend fun insertBars(bars: List<BarItem>){
        dao.insertBars(bars)
    }

    suspend fun deleteBars(){ dao.deleteBars() }

    fun getBarsByName(): LiveData<List<BarItem>?> = dao.getBarsByName()
    fun getBarsByCount(): LiveData<List<BarItem>?> = dao.getBarsByCount()
}