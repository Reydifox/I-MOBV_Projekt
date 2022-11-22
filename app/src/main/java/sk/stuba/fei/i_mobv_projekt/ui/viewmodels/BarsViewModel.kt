package sk.stuba.fei.i_mobv_projekt.ui.viewmodels

import androidx.lifecycle.*
import sk.stuba.fei.i_mobv_projekt.data.DataRepository
import sk.stuba.fei.i_mobv_projekt.data.db.model.BarItem
import sk.stuba.fei.i_mobv_projekt.helpers.Evento
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val bars: LiveData<List<BarItem>?> =
        liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
            emitSource(repository.dbBars())
        }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}
}