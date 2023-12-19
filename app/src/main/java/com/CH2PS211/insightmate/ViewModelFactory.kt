package com.CH2PS211.insightmate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.CH2PS211.insightmate.data.UploadRepository
import com.CH2PS211.insightmate.data.di.Injection
import com.CH2PS211.insightmate.ui.colordetector.ColorDetectorViewModel
import com.CH2PS211.insightmate.ui.moneydetector.MoneyDetectorViewModel

class ViewModelFactory(private val repository: UploadRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoneyDetectorViewModel::class.java) -> {
                MoneyDetectorViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ColorDetectorViewModel::class.java) -> {
                ColorDetectorViewModel(Injection.provideColorRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }

        @JvmStatic
        fun getColorInstance() =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideColorRepository())
            }.also { instance = it }
    }
}
