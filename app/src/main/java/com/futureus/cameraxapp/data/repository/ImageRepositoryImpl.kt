package com.futureus.cameraxapp.data.repository

import com.futureus.cameraxapp.BuildConfig
import com.futureus.cameraxapp.data.dto.ImageDto
import com.futureus.cameraxapp.domain.repository.ImageRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.upload
import io.github.jan.supabase.storage.uploadAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class ImageRepositoryImpl @Inject constructor(
    private val storage: Storage
) : ImageRepository {
    override suspend fun uploadImage(imageDto: ImageDto): ImageDto {
        withContext(Dispatchers.IO){
            if (imageDto.image.isNotEmpty()){
                val imageUrl = storage.from("image")
                imageUrl.uploadAsFlow(
                    path = imageDto.fileName,
                    data = imageDto.image,
                    upsert = true
                ).collect{
                    when(it) {
                        is UploadStatus.Progress -> println("Progress: ${it.totalBytesSend.toFloat() / it.contentLength * 100}%")
                        is UploadStatus.Success -> println("Success")
                    }
                }

                val url = imageUrl.createSignedUrl(path = imageDto.fileName, 1.days)

                val imageWithUri = imageDto.copy(uri = url)
            } else {
                throw IllegalArgumentException("Image is empty")
            }
        }

        return imageDto
    }
}