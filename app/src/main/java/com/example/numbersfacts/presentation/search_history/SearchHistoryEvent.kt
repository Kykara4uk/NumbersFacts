package com.example.numbersfacts.presentation.search_history

import com.example.numbersfacts.domain.models.NumberFact

sealed class SearchHistoryEvent {
    data class NavigateToDetailScreen(val numberFact: NumberFact) : SearchHistoryEvent()
    object GetFactButtonClick : SearchHistoryEvent()
    object GetRandomFactButtonClick : SearchHistoryEvent()
    data class NumberChanged(val number: Int?) : SearchHistoryEvent()
}