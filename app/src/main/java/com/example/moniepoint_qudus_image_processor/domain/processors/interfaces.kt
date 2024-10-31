package com.example.moniepoint_qudus_image_processor.domain.processors

import android.graphics.Bitmap

interface ImageProcessorFactory { fun createProcessor() : ImageProcessor }

interface ImageProcessor{
    suspend fun processImage(image : Bitmap, arg : Any) : Bitmap
}