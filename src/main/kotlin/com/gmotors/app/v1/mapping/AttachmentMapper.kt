package com.gmotors.app.v1.mapping

import com.gmotors.app.v1.dtos.AttachmentPartialCreateRequestDto
import com.gmotors.core.attachments.AttachmentCreateRequest
import com.gmotors.core.attachments.AttachmentType
import com.gmotors.core.mapping.Config
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.web.multipart.MultipartFile

@Mapper(config = Config::class)
interface AttachmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ext", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    fun toRequestModel(
        request: AttachmentPartialCreateRequestDto,
        type: AttachmentType,
        file: MultipartFile
    ): AttachmentCreateRequest

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ext", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    fun toRequestModel(
        request: AttachmentPartialCreateRequestDto,
        type: AttachmentType
    ): AttachmentCreateRequest
}
