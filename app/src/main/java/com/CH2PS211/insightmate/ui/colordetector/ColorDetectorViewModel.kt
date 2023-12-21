package com.CH2PS211.insightmate.ui.colordetector

import androidx.lifecycle.ViewModel
import com.CH2PS211.insightmate.data.ColorRepository
import java.io.File

class ColorDetectorViewModel(private val colorRepository: ColorRepository) : ViewModel() {
    fun uploadColorImage(file: File) = colorRepository.uploadColorImage(file)
}