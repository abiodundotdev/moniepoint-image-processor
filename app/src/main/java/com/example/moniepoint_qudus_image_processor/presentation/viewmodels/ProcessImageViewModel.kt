package com.example.moniepoint_qudus_image_processor.presentation.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moniepoint_qudus_image_processor.domain.processors.ImageProcessorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class ProcessImageViewModel  @Inject constructor() : ViewModel() {
    var _uiState =  mutableStateOf<ViewState>(ViewState.Loading)

    fun processImage(
        image: Bitmap,
        processTypes: List<ImageProcessorType>,
        args: List<Any>
    ): Bitmap {
        var processedImage = image;
        viewModelScope.launch {
            for ((processorType, arg) in processTypes.zip(args)) {
                val processor = processorType.createProcessor()
                processedImage = processor.processImage(processedImage, arg)
            }
        }
        return processedImage;
    }
}