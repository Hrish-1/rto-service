package com.gmotors.app.v1

import com.gmotors.app.v1.dtos.AttachmentPartialCreateRequestDto
import com.gmotors.app.v1.mapping.AttachmentMapper
import com.gmotors.core.attachments.AttachmentService
import com.gmotors.core.attachments.AttachmentType.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.InetAddress
import java.util.UUID

@RestController
@RequestMapping("/v1/attachments")
class AttachmentController(
    val mapper: AttachmentMapper,
    val service: AttachmentService
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestPart aadhaar: MultipartFile?,
        @RequestPart pan: MultipartFile?,
        @RequestPart insurance: MultipartFile?,
        @RequestPart chassis: MultipartFile?,
        @RequestPart request: AttachmentPartialCreateRequestDto
    ) {
        val txFiles = mapOf(AADHAAR to aadhaar, PAN to pan, INSURANCE to insurance, CHASSIS to chassis)
        txFiles.forEach { x ->
            x.value?.let { file ->
                val req = mapper.toRequestModel(request, x.key)
                service.create(req, file)
            }
        }
        if (request.createForms) {
            val txForms = listOf(FORM29, FORM30_1, FORM30_2)
            txForms.forEach {
                service.create(mapper.toRequestModel(request, it))
            }
        }
    }

    @GetMapping("/bytransaction/{txId}")
    @ResponseStatus(HttpStatus.OK)
    fun getByTxId(@PathVariable("txId") txId: UUID): Map<String, String> {
        return service.listByTransactionId(txId)
    }
}
