package com.ch2ps211.insightmate.data.api

import okhttp3.MultipartBody
import retrofit2.http.*

interface MoneyApiService {
    @Multipart
    @POST("/")
    suspend fun uploadImage(@Part file: MultipartBody.Part): FileUploadResponse
}