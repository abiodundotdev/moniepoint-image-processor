package com.example.moniepoint_qudus_image_processor.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ImageProcessorApiDataSource {
    @GET()
    suspend fun downloadImage(@Url imageUrl: String): ResponseBody
}