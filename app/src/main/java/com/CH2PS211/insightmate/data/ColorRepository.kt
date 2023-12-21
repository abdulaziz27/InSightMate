package com.CH2PS211.insightmate.data

import androidx.lifecycle.liveData
import com.CH2PS211.insightmate.data.api.ColorApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class ColorRepository private constructor(
    private val colorApiService: ColorApiService
) {
    fun uploadColorImage(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = colorApiService.uploadColorImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error("Gagal mengupload gambar"))
        }
    }

    companion object {
        @Volatile
        private var instance: ColorRepository? = null

        fun getInstance(colorApiService: ColorApiService) =
            instance ?: synchronized(this) {
                instance ?: ColorRepository(colorApiService)
            }.also { instance = it }
    }
}