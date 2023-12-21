package com.CH2PS211.insightmate.ui.moneydetector

import androidx.lifecycle.ViewModel
import com.CH2PS211.insightmate.data.MoneyRepository
import java.io.File

class MoneyDetectorViewModel(private val moneyRepository: MoneyRepository) : ViewModel() {
    fun uploadImage(file: File) = moneyRepository.uploadImage(file)
}