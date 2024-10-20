package com.example.moniepoint_qudus_image_processor.data

import dagger.hilt.EntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

interface ImageProcessorRepository {
    suspend fun downloadImage(imageUrl : String): Flow<DownloadImageResponse>;
}


@EntryPoint()
class ImageProcessorRepositoryImpl @Inject constructor(
    private val dataSource : ImageProcessorApiDataSource
) : ImageProcessorRepository{

    override suspend fun downloadImage(imageUrl: String): Flow<DownloadImageResponse> {
       return flow<DownloadImageResponse> {
           val response = dataSource.downloadImage(imageUrl);
           emit(DownloadImageResponse(response.toString()))
       }
    }

}

data class DownloadImageResponse(val content : String)