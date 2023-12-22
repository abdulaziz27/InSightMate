package com.ch2ps211.insightmate.ui.moneydetector

import androidx.lifecycle.ViewModel
import com.ch2ps211.insightmate.data.MoneyRepository
import java.io.File

class MoneyDetectorViewModel(private val moneyRepository: MoneyRepository) : ViewModel() {
    fun uploadImage(file: File) = moneyRepository.uploadImage(file)
}