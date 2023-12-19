package com.CH2PS211.insightmate.ui.colordetector

import androidx.lifecycle.ViewModel
import com.CH2PS211.insightmate.data.UploadRepository
import java.io.File

class ColorDetectorViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadColorImage(file: File) = repository.uploadColorImage(file)
}