package com.CH2PS211.insightmate.data.api

import okhttp3.MultipartBody
import retrofit2.http.*


interface ColorApiService {
    @Multipart
    @POST("/")
    suspend fun uploadColorImage(@Part file: MultipartBody.Part): ColorUploadResponse
}