package com.example.numbersfacts.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NumbersInterface {
    @GET("{number}")
    suspend fun getNumberFact(@Path("number") number: Int) : String

    @GET("random/math")
    suspend fun getRandomNumberFact() : String
}