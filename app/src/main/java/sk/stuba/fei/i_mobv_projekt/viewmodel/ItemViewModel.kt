package sk.stuba.fei.i_mobv_projekt.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stuba.fei.i_mobv_projekt.database.PubDatabase
import sk.stuba.fei.i_mobv_projekt.repository.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ItemRepository(PubDatabase.getDatabase(application))
    var list = repository.list

    init {
        refresh()
    }

    fun refresh()
    {
        viewModelScope.launch {
            repository.refresh()
        }
    }

    fun delete(position: Int) {
        repository.delete(position)
    }

    fun deleteByID(id : String) {
        println("delete called! $id")
    }

}