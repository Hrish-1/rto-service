package com.gmotors.app.v1

import com.gmotors.app.v1.dtos.AttachmentPartialCreateRequestDto
import com.gmotors.app.v1.mapping.AttachmentMapper
import com.gmotors.core.attachments.AttachmentService
import com.gmotors.core.attachments.AttachmentType
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun create(
        @RequestPart aadhaar: MultipartFile?,
        @RequestPart pan: MultipartFile?,
        @RequestPart insurance: MultipartFile?,
        @RequestPart chassis: MultipartFile?,
        @RequestPart request: AttachmentPartialCreateRequestDto
    ) {
        create(request, AADHAAR, aadhaar)
        create(request, PAN, pan)
        create(request, INSURANCE, insurance)
        create(request, CHASSIS, chassis)

        if (!request.createForms) return

        create(request, FORM29)
        create(request, FORM30_1)
        create(request, FORM30_2)
    }

    @GetMapping("/bytransaction/{txId}")
    fun getByTxId(@PathVariable("txId") txId: UUID): Map<String, String> {
        return service.listByTransactionId(txId)
    }

    private fun create(
        request: AttachmentPartialCreateRequestDto,
        type: AttachmentType,
        file: MultipartFile?
    ) {
        val req = mapper.toRequestModel(request, type)
        file?.let { service.create(req, it) }
    }

    private fun create(request: AttachmentPartialCreateRequestDto, type: AttachmentType) {
        val req = mapper.toRequestModel(request, type)
        service.create(req)
    }
}
