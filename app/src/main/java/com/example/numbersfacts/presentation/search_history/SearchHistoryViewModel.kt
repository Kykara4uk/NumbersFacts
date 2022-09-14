package com.example.numbersfacts.presentation.search_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.numbersfacts.domain.models.NumberFact
import com.example.numbersfacts.domain.repository.NumbersRepository
import com.example.numbersfacts.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel  @Inject constructor(
    private val numbersRepository: NumbersRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _number = MutableLiveData<Int?>()
    val number: LiveData<Int?>
        get() = _number

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _history = MutableLiveData<List<NumberFact>>()
    val history: LiveData<List<NumberFact>>
        get() = _history

    private val _navigation = Channel<SearchHistoryEvent>(Channel.BUFFERED)
    val navigation = _navigation.receiveAsFlow()

    private fun navigateToDetailScreen(numberFact: NumberFact) {
        viewModelScope.launch {
            _navigation.send(SearchHistoryEvent.NavigateToDetailScreen(numberFact))
        }
    }


    init {
        getHistory()
    }

    fun onEvent(event: SearchHistoryEvent){
        when(event){
            is SearchHistoryEvent.GetFactButtonClick -> {
                getNumberFact()
            }
            is SearchHistoryEvent.GetRandomFactButtonClick -> {
                getRandomNumberFact()
            }
            is SearchHistoryEvent.NumberChanged -> {
                _number.postValue(event.number)
            }
            else -> {}
        }
    }
    private fun getNumberFact(){
        viewModelScope.launch(Dispatchers.IO) {
            number.value?.let { number ->
                numbersRepository
                    .getNumberFact(number)
                    .collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.postValue(result.isLoading)
                            }
                            is Resource.Error -> {
                                result.message?.let { _error.emit(it) }
                            }
                            is Resource.Success -> {
                                result.data?.let {
                                    navigateToDetailScreen(it)
                                }
                            }
                        }
                    }
            }?: _error.emit("Enter number first")
        }

    }

    private fun getRandomNumberFact(){
        viewModelScope.launch(Dispatchers.IO) {
            numbersRepository
                .getRandomNumberFact()
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.postValue(result.isLoading)
                        }
                        is Resource.Error -> {
                            result.message?.let { _error.emit(it) }
                        }
                        is Resource.Success -> {
                            result.data?.let {
                                navigateToDetailScreen(it)
                            }
                        }
                    }
                }
        }

    }

    fun getHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            numbersRepository
                .getFactsHistory()
                .collect{
                    result ->
                    when(result){
                        is Resource.Loading -> {
                            _isLoading.postValue(result.isLoading)
                        }
                        is Resource.Error -> {
                            result.message?.let { _error.emit(it) }
                        }
                        is Resource.Success -> {
                            result.data?.let { _history.postValue(it) }
                        }
                    }
                }
        }
    }


}