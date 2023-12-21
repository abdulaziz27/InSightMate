package com.CH2PS211.insightmate.data

import androidx.lifecycle.liveData
import com.CH2PS211.insightmate.data.api.DocumentApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class DocumentRepository private constructor(
    private val documentApiService: DocumentApiService
) {
    fun uploadDocumentImage(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = documentApiService.uploadDocumentImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error("Gagal mengupload gambar"))
        }
    }

    companion object {
        @Volatile
        private var instance: DocumentRepository? = null

        fun getInstance(documentApiService: DocumentApiService) =
            instance ?: synchronized(this) {
                instance ?: DocumentRepository(documentApiService)
            }.also { instance = it }
    }
}