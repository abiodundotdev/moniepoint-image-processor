package com.example.moniepoint_qudus_image_processor.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moniepoint_qudus_image_processor.data.DownloadImageResponse
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
    var uiState = mutableStateOf<ViewState>(ViewState.Loading)

    fun downloadImage(url: String) {
        viewModelScope.launch {
            try {
                val responseBody = withContext(Dispatchers.IO) {
                    repository.downloadImage(url).collect{ value ->
                        uiState.value = ViewState.Success<DownloadImageResponse>()
                    }
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

sealed interface ViewState {
    data class Error(val errorMessage : String) : ViewState
    data object Loading : ViewState
    data class Success<T>(val value : T) : ViewState
}