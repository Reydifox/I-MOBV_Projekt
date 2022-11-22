package sk.stuba.fei.i_mobv_projekt.ui.viewmodels

import androidx.lifecycle.*
import sk.stuba.fei.i_mobv_projekt.data.DataRepository
import sk.stuba.fei.i_mobv_projekt.helpers.Evento
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data.NearbyBar
import sk.stuba.fei.i_mobv_projekt.ui.widget.detailList.BarDetailItem
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DataRepository) : ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val bar = MutableLiveData<NearbyBar>(null)
    val type = bar.map { it?.tags?.getOrDefault("amenity", "") ?: "" }
    val details: LiveData<List<BarDetailItem>> = bar.switchMap {
        liveData {
            it?.let {
                emit(it.tags.map {
                    BarDetailItem(it.key, it.value)
                })
            } ?: emit(emptyList<BarDetailItem>())
        }
    }

    fun loadBar(id: String) {
        if (id.isBlank())
            return
        viewModelScope.launch {
            loading.postValue(true)
            bar.postValue(repository.apiBarDetail(id) { _message.postValue(Evento(it)) })
            loading.postValue(false)
        }
    }
}