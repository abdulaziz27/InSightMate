package com.CH2PS211.insightmate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.CH2PS211.insightmate.data.ColorRepository
import com.CH2PS211.insightmate.data.DocumentRepository
import com.CH2PS211.insightmate.data.MoneyRepository
import com.CH2PS211.insightmate.ui.colordetector.ColorDetectorViewModel
import com.CH2PS211.insightmate.ui.documentreader.DocumentReaderViewModel
import com.CH2PS211.insightmate.ui.moneydetector.MoneyDetectorViewModel

class ViewModelFactory(
    private val moneyRepository: MoneyRepository,
    private val colorRepository: ColorRepository,
    private val documentRepository: DocumentRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoneyDetectorViewModel::class.java) -> {
                MoneyDetectorViewModel(moneyRepository) as T
            }
            modelClass.isAssignableFrom(ColorDetectorViewModel::class.java) -> {
                ColorDetectorViewModel(colorRepository) as T
            }
            modelClass.isAssignableFrom(DocumentReaderViewModel::class.java) -> {
                DocumentReaderViewModel(documentRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
