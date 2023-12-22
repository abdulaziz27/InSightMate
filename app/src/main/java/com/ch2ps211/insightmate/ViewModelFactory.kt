package com.ch2ps211.insightmate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps211.insightmate.data.ColorRepository
import com.ch2ps211.insightmate.data.DocumentRepository
import com.ch2ps211.insightmate.data.MoneyRepository
import com.ch2ps211.insightmate.ui.colordetector.ColorDetectorViewModel
import com.ch2ps211.insightmate.ui.documentreader.DocumentReaderViewModel
import com.ch2ps211.insightmate.ui.moneydetector.MoneyDetectorViewModel

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
