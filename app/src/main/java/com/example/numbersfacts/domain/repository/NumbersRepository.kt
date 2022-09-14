package com.example.numbersfacts.domain.repository

import com.example.numbersfacts.data.remote.api.NumbersInterface
import com.example.numbersfacts.domain.models.NumberFact
import com.example.numbersfacts.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NumbersRepository  {
    suspend fun getNumberFact(number: Int) : Flow<Resource<NumberFact>>

    suspend fun getRandomNumberFact() : Flow<Resource<NumberFact>>

    suspend fun getFactsHistory() : Flow<Resource<List<NumberFact>>>

    suspend fun updateFactLastViewed(numberFact: NumberFact)
}