package com.gmotors.core.attachments

import com.gmotors.core.HtmlGenerator
import com.gmotors.core.transactions.TransactionRepository
import com.gmotors.core.StorageService
import com.gmotors.core.ext
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class AttachmentService(
    val repo: AttachmentRepository,
    val htmlGenerator: HtmlGenerator,
    val storageService: StorageService,
    val txRepo: TransactionRepository
) {
    fun create(request: AttachmentCreateRequest, file: MultipartFile) {
        val id = storageService.store(file)
        val contentType = file.contentType
        requireNotNull(contentType) { "Content type is required" }
        val ext = file.ext()
        val request =  request.copy(id = id, contentType = contentType, ext = ext)
        repo.create(request)
    }

    fun create(request: AttachmentCreateRequest) {
        val tx = txRepo.get(request.entityId)
        requireNotNull(tx) { "Transaction not found" }
        val html = htmlGenerator.generate(request.type.templateName(), mapOf())
        val id = storageService.mapToPdfAndStore(html)
        val request = request.copy(id = id, contentType = MediaType.APPLICATION_PDF_VALUE, ext = "pdf")
        repo.create(request)
    }

    fun listByTransactionId(transactionId: UUID): Map<String, String> {
        return repo.listByTransaction(transactionId)
    }
}