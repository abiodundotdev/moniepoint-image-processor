package com.example.moniepoint_qudus_image_processor.core

import com.example.moniepoint_qudus_image_processor.core.constants.Endpoints
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorApiDataSource
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorRepository
import com.example.moniepoint_qudus_image_processor.data.ImageProcessorRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module()
@InstallIn(SingletonComponent::class)
object AppModule {
    val retrofitBuilder: Retrofit = Retrofit.Builder()
        .baseUrl(Endpoints.baseUrl)
        .build()

    @Provides
    fun provideImageProcessorApiDataSource(
    ): ImageProcessorApiDataSource {
        return retrofitBuilder.create(ImageProcessorApiDataSource::class.java)
    }

    @Provides
    fun provideImageProcessorRepository(apiDataSource: ImageProcessorApiDataSource): ImageProcessorRepository =
        ImageProcessorRepositoryImpl(apiDataSource)
}