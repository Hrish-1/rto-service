package com.gmotors.infra.filesystem

import com.gmotors.core.FileSystem
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.outputStream

@Service
class RealFileSystem : FileSystem {

    override fun exists(path: Path): Boolean {
        return Files.exists(path)
    }

    override fun createDirectories(path: Path) {
        Files.createDirectories(path)
    }

    override fun copy(input: InputStream, target: Path, vararg options: CopyOption) {
        Files.copy(input, target, *options)
    }

    override fun createFile(path: Path): Path {
        return Files.createFile(path)
    }

    override fun getOutputStream(path: Path): OutputStream {
        return path.outputStream()
    }
}