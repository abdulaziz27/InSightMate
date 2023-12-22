package com.ch2ps211.insightmate.ui.colordetector

import androidx.lifecycle.ViewModel
import com.ch2ps211.insightmate.data.ColorRepository
import java.io.File

class ColorDetectorViewModel(private val colorRepository: ColorRepository) : ViewModel() {
    fun uploadColorImage(file: File) = colorRepository.uploadColorImage(file)
}