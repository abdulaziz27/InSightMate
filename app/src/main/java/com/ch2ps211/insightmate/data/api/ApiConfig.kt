package com.ch2ps211.insightmate.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private const val BASE_URL = "https://predictismbanknotenew-xiiss5i4hq-et.a.run.app"
    private const val BASE_COLOR_URL = "https://predictismcolornew-xiiss5i4hq-et.a.run.app"
    private const val BASE_DOCUMENT_URL = "https://predictismdocument-xiiss5i4hq-et.a.run.app"

    fun getApiService(): MoneyApiService {
        return createService(BASE_URL)
    }

    fun getColorDetectorApiService(): ColorApiService {
        return createService(BASE_COLOR_URL)
    }

    fun getDocumentReaderApiService(): DocumentApiService {
        return createService(BASE_DOCUMENT_URL)
    }

    private inline fun <reified T> createService(baseUrl: String): T {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(T::class.java)
    }
}