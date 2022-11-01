package sk.stuba.fei.i_mobv_projekt.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stuba.fei.i_mobv_projekt.database.PubDatabase
import sk.stuba.fei.i_mobv_projekt.fragment.ItemAdapter
import sk.stuba.fei.i_mobv_projekt.placeholder.PlaceholderContent
import sk.stuba.fei.i_mobv_projekt.repository.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ItemRepository(PubDatabase.getDatabase(application))

    init {
        refresh()
    }

    fun refresh()
    {
        viewModelScope.launch {
            repository.refresh()
            prepareData()
        }
    }

    private fun prepareData()
    {
        repository.list.value?.let {
            PlaceholderContent.parseData(it)
            ItemAdapter.refresh(PlaceholderContent.ITEMS)
        }
    }

}