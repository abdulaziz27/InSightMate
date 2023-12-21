package com.CH2PS211.insightmate.data.di

import com.CH2PS211.insightmate.data.ColorRepository
import com.CH2PS211.insightmate.data.DocumentRepository
import com.CH2PS211.insightmate.data.MoneyRepository
import com.CH2PS211.insightmate.data.api.ApiConfig

object Injection {
    fun provideMoneyRepository(): MoneyRepository {
        val moneyApiService = ApiConfig.getApiService()
        return MoneyRepository.getInstance(moneyApiService)
    }

    fun provideColorRepository(): ColorRepository {
        val colorApiService = ApiConfig.getColorDetectorApiService()
        return ColorRepository.getInstance(colorApiService)
    }

    fun provideDocumentRepository(): DocumentRepository {
        val documentApiService = ApiConfig.getDocumentReaderApiService()
        return DocumentRepository.getInstance(documentApiService)
    }
}