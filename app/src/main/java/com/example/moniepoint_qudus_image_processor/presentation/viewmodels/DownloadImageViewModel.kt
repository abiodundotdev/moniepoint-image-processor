package com.example.moniepoint_qudus_image_processor.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorApiDataSource
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel()
class DownloadImageViewModel @Inject constructor(
        private val repository : ImageProcessorRepository
)  : ViewModel() {
    var uiState = mutableStateOf<Bitmap?>(null)
        private set

    fun downloadImage(url: String) {
        viewModelScope.launch {
            try {
                val responseBody = withContext(Dispatchers.IO) {
                    apiService.downloadImage(url)
                }
                val inputStream: InputStream = responseBody.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                uiState.value = bitmap
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

sealed class ViewState() {
    class Error(val errorMessage : String) : ViewState()
    class DatabaseError : Error("Database cannot be reached")
    class UnknownError : Error("An unknown error has occurred")
}