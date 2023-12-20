package com.CH2PS211.insightmate.ui.documentreader

import androidx.lifecycle.ViewModel
import com.CH2PS211.insightmate.data.UploadRepository
import java.io.File

class DocumentReaderViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadDocumentImage(file: File) = repository.uploadDocumentImage(file)
}