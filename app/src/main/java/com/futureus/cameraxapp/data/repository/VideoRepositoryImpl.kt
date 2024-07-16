package com.futureus.cameraxapp.data.repository

import com.futureus.cameraxapp.data.dto.VideoDto
import com.futureus.cameraxapp.domain.repository.VideoRepository
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.uploadAsFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class VideoRepositoryImpl @Inject constructor(
    private val storage: Storage
) : VideoRepository {
    override suspend fun uploadVideo(videoDto: VideoDto): VideoDto {
        if (videoDto.video.isNotEmpty()) {
            val videoUrl = storage.from("image")

            videoUrl.uploadAsFlow(
                path = videoDto.fileName,
                data = videoDto.video,
                upsert = true
            ).collect{
                when(it) {
                    is UploadStatus.Progress -> println("Progress: ${it.totalBytesSend.toFloat() / it.contentLength * 100}%")
                    is UploadStatus.Success -> println("Success")
                }
            }

            val url = videoUrl.createSignedUrl(path = videoDto.fileName, 1.days)

            println("Video URL: $url")

            val videoDtoWithUrl = videoDto.copy(uri = url)
            return videoDtoWithUrl
        } else {
            throw IllegalArgumentException("Video is empty")
        }
    }

}