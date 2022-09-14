package com.example.numbersfacts.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numbersfacts.domain.models.NumberFact
import com.example.numbersfacts.domain.repository.NumbersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val numbersRepository: NumbersRepository
) : ViewModel() {
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _fact = MutableLiveData<NumberFact>()
    val fact: LiveData<NumberFact>
        get() = _fact

    fun setFact(fact: NumberFact){
        _fact.postValue(fact)
        updateFactLastViewed(fact)
    }


    private fun updateFactLastViewed(numberFact: NumberFact){
        viewModelScope.launch(Dispatchers.IO) {
            numbersRepository.updateFactLastViewed(numberFact)
        }

    }
}