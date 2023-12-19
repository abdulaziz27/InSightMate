package com.CH2PS211.insightmate.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("/")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): FileUploadResponse

    @Multipart
    @POST("/")
    suspend fun uploadColorImage(
        @Part file: MultipartBody.Part
    ): ColorUploadResponse
}

