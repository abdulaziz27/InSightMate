package com.CH2PS211.insightmate.data.api

import okhttp3.MultipartBody
import retrofit2.http.*

interface DocumentApiService {
    @Multipart
    @POST("/")
    suspend fun uploadDocumentImage(@Part file: MultipartBody.Part): DocumentUploadResponse
}