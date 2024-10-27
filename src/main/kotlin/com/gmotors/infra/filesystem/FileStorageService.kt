package com.gmotors.infra.filesystem

import com.gmotors.core.FileSystem
import com.gmotors.core.HtmlContent
import com.gmotors.core.StorageService
import com.gmotors.core.ext
import com.lowagie.text.Document
import com.lowagie.text.html.simpleparser.HTMLWorker
import com.lowagie.text.pdf.PdfWriter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.StringReader
import java.nio.file.StandardCopyOption
import java.util.UUID

@Service
class FileStorageService(val config: StorageConfig, val fileSystem: FileSystem) : StorageService {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    final override fun init() {
        if (!fileSystem.exists(config.getRootPath())) {
            fileSystem.createDirectories(config.getRootPath())
        }
    }

    init { init() }

    @Throws(IOException::class)
    override fun store(file: MultipartFile): UUID {
        require(!file.isEmpty) { "File cannot be empty" }
        val fileId = UUID.randomUUID()
        val destinationFile = config
            .getRootPath()
            .resolve("$fileId.${file.ext()}")
            .normalize()
            .toAbsolutePath()
        require(destinationFile.parent.startsWith(config.getRootPath().toAbsolutePath())) {
            "Destination file parent path should start with root path"
        }
        file.inputStream.use { inputStream ->
            fileSystem.copy(
                inputStream,
                destinationFile,
                StandardCopyOption.REPLACE_EXISTING
            )
        }
        return fileId
    }

    @Throws(IOException::class)
    override fun mapToPdfAndStore(html: HtmlContent): UUID {
        val document = Document()
        val id = UUID.randomUUID()
        val destinationFile = config
            .getRootPath()
            .resolve("$id.pdf")
            .normalize()
            .toAbsolutePath()
        val file = fileSystem.createFile(destinationFile)
        PdfWriter.getInstance(document, fileSystem.getOutputStream(file))
        document.open()
        val htmlWorker = HTMLWorker(document)
        htmlWorker.parse(StringReader(html.value()))
        document.close()
        return id
    }
}