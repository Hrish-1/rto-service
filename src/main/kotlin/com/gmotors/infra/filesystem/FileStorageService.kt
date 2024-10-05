package com.gmotors.infra.filesystem

import com.lowagie.text.Document
import com.lowagie.text.html.simpleparser.HTMLWorker
import com.lowagie.text.pdf.PdfWriter
import org.apache.commons.logging.LogFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.OutputStream
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.UUID
import kotlin.io.path.outputStream
import kotlin.io.path.reader

@Service
class FileStorageService(val config: StorageConfig) : StorageService {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun init() {
        if (!Files.exists(config.getRootPath())) {
            Files.createDirectories(config.getRootPath())
        }
    }

    @Throws(IOException::class)
    override fun store(file: MultipartFile): UUID {
        init()
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
            Files.copy(
                inputStream,
                destinationFile,
                StandardCopyOption.REPLACE_EXISTING
            )
        }
        return fileId
    }

    @Throws(IOException::class)
    override fun mapToPdfAndStore(html: String): UUID {
        init()
        val document = Document()
        val id = UUID.randomUUID()
        val destinationFile = config
            .getRootPath()
            .resolve("$id.pdf")
            .normalize()
            .toAbsolutePath()
        val file = Files.createFile(destinationFile)
        PdfWriter.getInstance(document, file.outputStream())
        document.open()
        val htmlWorker = HTMLWorker(document)
        htmlWorker.parse(StringReader(html))
        document.close()
        return id
    }
}