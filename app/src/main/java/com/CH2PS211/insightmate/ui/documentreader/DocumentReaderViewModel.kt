package com.CH2PS211.insightmate.ui.documentreader

import androidx.lifecycle.ViewModel
import com.CH2PS211.insightmate.data.DocumentRepository
import java.io.File

class DocumentReaderViewModel(private val documentRepository: DocumentRepository) : ViewModel() {
    fun uploadDocumentImage(file: File) = documentRepository.uploadDocumentImage(file)
}