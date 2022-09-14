package com.example.numbersfacts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.numbersfacts.data.local.models.NumberFactEntity

@Dao
interface NumbersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumberFact(numberFactEntity: NumberFactEntity)

    @Query("SELECT * FROM facts WHERE number == :number AND fact == :fact")
    suspend fun getNumberFact(number: Int, fact: String) : NumberFactEntity?

    @Query("SELECT * FROM facts")
    suspend fun getAllNumberFacts(): List<NumberFactEntity>
}