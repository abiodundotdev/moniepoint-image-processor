package com.example.moniepoint_qudus_image_processor.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moniepoint_qudus_image_processor.data.DownloadImageResponse
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorApiDataSource
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel()
class DownloadImageViewModel @Inject constructor(
        private val repository : ImageProcessorRepository
)  : ViewModel() {
    private var _uiState =  mutableStateOf<ViewState>(ViewState.Loading)
    val uiState = _uiState

    fun downloadImage(url: String) {
        viewModelScope.launch {
            val responseBody = withContext(Dispatchers.IO) {
                repository.downloadImage(url)
                    .catch {
                        _uiState.value = ViewState.Error("Failed to download image")
                    }
                    .collect { value ->
                        _uiState.value = ViewState.Success<DownloadImageResponse>(value)
                    }
            }
        }
    }
}

sealed interface ViewState {
    data class Error(val errorMessage : String) : ViewState
    data object Loading : ViewState
    data class Success<T>(val value : T) : ViewState
}
