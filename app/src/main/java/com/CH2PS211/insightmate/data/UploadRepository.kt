package com.CH2PS211.insightmate.data

import androidx.lifecycle.liveData
import com.CH2PS211.insightmate.data.api.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class UploadRepository private constructor(
    private val apiService: ApiService
) {

    fun uploadImage(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error("Gagal mengupload gambar"))
        }

    }

    fun uploadColorImage(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadColorImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error("Gagal mengupload gambar"))
        }
    }

    fun uploadDocumentImage(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadDocumentImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error("Gagal mengupload gambar"))
        }
    }

    companion object {
        @Volatile
        private var instance: UploadRepository? = null

        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(apiService)
            }.also { instance = it }

        fun getColorInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(apiService)
            }.also { instance = it }

        fun getDocumentInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(apiService)
            }.also { instance = it }
    }
}