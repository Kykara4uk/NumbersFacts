package com.example.numbersfacts.data.remote.api

import android.provider.SyncStateContract
import com.example.numbersfacts.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NumbersClient {
    fun getClient(): NumbersInterface {

        val requestInterceptor = Interceptor {chain->
            val url =chain
                .request()
                .url()
                .newBuilder()
                .build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.NUMBERS_BASE_API)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NumbersInterface::class.java)
    }
}