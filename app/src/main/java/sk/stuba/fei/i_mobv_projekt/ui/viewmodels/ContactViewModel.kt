package sk.stuba.fei.i_mobv_projekt.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stuba.fei.i_mobv_projekt.data.DataRepository
import sk.stuba.fei.i_mobv_projekt.data.api.ContactListResponse
import sk.stuba.fei.i_mobv_projekt.data.api.UserResponse
import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem
import sk.stuba.fei.i_mobv_projekt.helpers.Evento
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data.Contact

class ContactViewModel(private val repository: DataRepository): ViewModel()  {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    private val _contacts = MutableLiveData<List<ContactItem>>()
    val contacts: LiveData<List<ContactItem>>
        get() = _contacts

    init {
        refreshData()
    }

    fun addFriend(name: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiContactAdd(
                Contact(name)
            ) { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiContactList { _message.postValue(Evento(it)) }
            _contacts.value = repository.dbContacts()
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}
}