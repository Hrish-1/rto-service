package com.gmotors.core

import org.springframework.web.multipart.MultipartFile
import java.util.UUID


interface StorageService {
    fun init()

    fun store(file: MultipartFile): UUID

    fun mapToPdfAndStore(html: HtmlContent): UUID
}