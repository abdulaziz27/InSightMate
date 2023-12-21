package com.CH2PS211.insightmate.data

import androidx.lifecycle.liveData
import com.CH2PS211.insightmate.data.api.MoneyApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class MoneyRepository private constructor(
    private val moneyApiService: MoneyApiService
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
            val successResponse = moneyApiService.uploadImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error("Gagal mengupload gambar"))
        }
    }

    companion object {
        @Volatile
        private var instance: MoneyRepository? = null

        fun getInstance(moneyApiService: MoneyApiService) =
            instance ?: synchronized(this) {
                instance ?: MoneyRepository(moneyApiService)
            }.also { instance = it }
    }
}