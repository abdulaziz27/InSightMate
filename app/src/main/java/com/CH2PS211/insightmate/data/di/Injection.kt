package com.CH2PS211.insightmate.data.di

import com.CH2PS211.insightmate.data.UploadRepository
import com.CH2PS211.insightmate.data.api.ApiConfig

object Injection {
    fun provideRepository(): UploadRepository {
        val apiService = ApiConfig.getApiService()
        return UploadRepository.getInstance(apiService)
    }

    fun provideColorRepository(): UploadRepository {
        val colorApiService = ApiConfig.getColorDetectorApiService()
        return UploadRepository.getInstance(colorApiService)
    }
}