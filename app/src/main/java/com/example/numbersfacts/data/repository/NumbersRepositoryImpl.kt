package com.example.numbersfacts.data.repository

import com.example.numbersfacts.data.local.NumbersDatabase
import com.example.numbersfacts.data.local.models.NumberFactEntity
import com.example.numbersfacts.data.mappers.toNumberFact
import com.example.numbersfacts.data.mappers.toNumberFactEntity
import com.example.numbersfacts.data.remote.api.NumbersInterface
import com.example.numbersfacts.domain.models.NumberFact
import com.example.numbersfacts.domain.repository.NumbersRepository
import com.example.numbersfacts.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NumbersRepositoryImpl @Inject constructor(
    private val apiService: NumbersInterface,
    private val localDatabase: NumbersDatabase
) : NumbersRepository {

    private val dao = localDatabase.dao()

    override suspend fun getNumberFact(number: Int): Flow<Resource<NumberFact>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val fact = apiService.getNumberFact(number)
                val numberFactEntity = NumberFactEntity(fact = htmlCorrectString(fact), number = number, lastViewed = System.currentTimeMillis())
                dao.insertNumberFact(numberFactEntity)
                emit(Resource.Loading(false))
                emit(Resource.Success(numberFactEntity.toNumberFact()))
            } catch (e: Exception){
                emit(Resource.Loading(false))
                emit(Resource.Error("Error loading data"))
            }

        }
    }

    override suspend fun getRandomNumberFact(): Flow<Resource<NumberFact>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val fact = apiService.getRandomNumberFact()
                val number = fact.split(" is")[0]
                val numberFactEntity = NumberFactEntity(fact = htmlCorrectString(fact), number = number.toInt(), lastViewed = System.currentTimeMillis())
                dao.insertNumberFact(numberFactEntity)
                emit(Resource.Loading(false))
                emit(Resource.Success(numberFactEntity.toNumberFact()))
            } catch (e: Exception){
                emit(Resource.Loading(false))
                emit(Resource.Error("Error loading data"))
            }

        }
    }

    override suspend fun getFactsHistory(): Flow<Resource<List<NumberFact>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val facts = dao.getAllNumberFacts()
                emit(Resource.Loading(false))
                emit(Resource.Success(facts.map { it.toNumberFact() }.sortedBy { it.lastViewed }))
            } catch (e: Exception){
                emit(Resource.Loading(false))
                emit(Resource.Error("Error loading data"))
            }
        }
    }

    override suspend fun updateFactLastViewed(numberFact: NumberFact) {
        val numberFactEntity = numberFact.toNumberFactEntity( System.currentTimeMillis())
        dao.insertNumberFact(numberFactEntity)
    }

    private fun htmlCorrectString(string: String) = string.replace("^{", "<sup><small>").replace("}", "</small></sup>").replace("^{", "<sup><small>").replace("}", "</small></sup>")
}