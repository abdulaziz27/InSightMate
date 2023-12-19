package com.CH2PS211.insightmate.ui.documentreader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DocumentReaderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Document Reader Fragment"
    }
    val text: LiveData<String> = _text
}