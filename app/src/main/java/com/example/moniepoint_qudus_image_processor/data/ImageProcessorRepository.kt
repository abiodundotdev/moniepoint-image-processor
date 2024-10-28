package com.example.moniepoint_qudus_image_processor.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.EntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStream
import javax.inject.Inject

interface ImageProcessorRepository {
    suspend fun downloadImage(imageUrl : String): Flow<DownloadImageResponse>;
}


class ImageProcessorRepositoryImpl @Inject constructor(
    private val dataSource : ImageProcessorApiDataSource
) : ImageProcessorRepository{

    override suspend fun downloadImage(imageUrl: String): Flow<DownloadImageResponse> {
       return flow<DownloadImageResponse> {
           val response = dataSource.downloadImage(imageUrl);
           val bitmapImage = BitmapFactory.decodeStream(response.byteStream())
           emit(DownloadImageResponse(bitmapImage))
       }
    }

}

data class DownloadImageResponse(val image : Bitmap)