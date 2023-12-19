package com.CH2PS211.insightmate.ui.moneydetector

import androidx.lifecycle.ViewModel
import com.CH2PS211.insightmate.data.UploadRepository
import java.io.File

class MoneyDetectorViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadImage(file: File) = repository.uploadImage(file)
}