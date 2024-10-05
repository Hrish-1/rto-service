package com.gmotors.infra.filesystem

import org.springframework.web.multipart.MultipartFile
import java.util.UUID


interface StorageService {
    fun init()

    fun store(file: MultipartFile): UUID

    fun mapToPdfAndStore(html: String): UUID
}