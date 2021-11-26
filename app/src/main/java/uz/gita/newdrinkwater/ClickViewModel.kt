package uz.gita.newdrinkwater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClickViewModel : ViewModel() {
    private val _clickLiveData = MutableLiveData<Unit>()
    val clickLiveData: LiveData<Unit> get() = _clickLiveData

    init {
        viewModelScope.launch(Dispatchers.Main) {
            delay(1500)
            _clickLiveData.value = Unit
        }
    }
}