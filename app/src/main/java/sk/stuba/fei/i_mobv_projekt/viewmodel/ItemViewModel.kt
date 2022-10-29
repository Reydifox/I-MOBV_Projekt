package sk.stuba.fei.i_mobv_projekt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private val _data = MutableLiveData("")
    val data: LiveData<String>
        get() = _data

    init {
        // todo
    }

    fun refresh()
    {

    }

}