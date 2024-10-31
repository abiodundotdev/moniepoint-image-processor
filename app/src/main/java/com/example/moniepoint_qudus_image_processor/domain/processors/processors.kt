package com.example.moniepoint_qudus_image_processor.domain.processors

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.geometry.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class GrayScaleImageProcessor : ImageProcessor{
    override suspend fun processImage(image: Bitmap, arg: Any): Bitmap {
        return withContext(Dispatchers.IO){
            val grayscaleBitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(grayscaleBitmap)
            val paint = android.graphics.Paint()
            val colorMatrix = android.graphics.ColorMatrix().apply {
                setSaturation(0f)
            }
            paint.colorFilter = android.graphics.ColorMatrixColorFilter(colorMatrix)
            canvas.drawBitmap(image, 0f, 0f, paint)
            grayscaleBitmap
        }
    }
}

class ResizeImageProcessor : ImageProcessor {
    override suspend fun processImage(image: Bitmap, arg: Any): Bitmap {
        arg as Size
        return withContext(Dispatchers.IO){
            Bitmap.createScaledBitmap(image, arg.width.toInt(), arg.height.toInt(), true)
        }
    }
}

class CompressImageProcessor : ImageProcessor {
    override suspend fun processImage(image: Bitmap, arg: Any): Bitmap {
        arg as Int
        return withContext(Dispatchers.IO){
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, arg, outputStream)
            val byteArray = outputStream.toByteArray()
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}

class SepiaImageProcessor : ImageProcessor {
    override suspend fun processImage(image: Bitmap, arg: Any): Bitmap {
        return withContext(Dispatchers.IO) {
            val sepiaBitmap =
                Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(sepiaBitmap)
            val paint = android.graphics.Paint()
            val colorMatrix = android.graphics.ColorMatrix().apply {
                set(
                    floatArrayOf(
                        0.393f, 0.769f, 0.189f, 0f, 0f,
                        0.349f, 0.686f, 0.168f, 0f, 0f,
                        0.272f, 0.534f, 0.131f, 0f, 0f,
                        0f, 0f, 0f, 1f, 0f
                    )
                )
            }
            paint.colorFilter = android.graphics.ColorMatrixColorFilter(colorMatrix)
            canvas.drawBitmap(image, 0f, 0f, paint)
            sepiaBitmap
        }
    }
}

enum class ImageProcessorType : ImageProcessorFactory {
    RESIZE {
        override fun createProcessor(): ImageProcessor {
            return  ResizeImageProcessor()
        }
    },
    COMPRESS {
        override fun createProcessor(): ImageProcessor {
            return  CompressImageProcessor()
        }
    },
    GRAYSCALE {
        override fun createProcessor(): ImageProcessor {
            return  GrayScaleImageProcessor()
        }
    },
    SEPIA {
        override fun createProcessor(): ImageProcessor {
            return  SepiaImageProcessor()
        }
    }
}

data class Size(val width : Double, val height  : Double)