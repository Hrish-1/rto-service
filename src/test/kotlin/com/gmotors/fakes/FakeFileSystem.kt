package com.gmotors.fakes

import com.gmotors.core.FileSystem
import org.mockito.Mockito.mock
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.CopyOption
import java.nio.file.Path

class FakeFileSystem : FileSystem {
    override fun exists(path: Path): Boolean {
        return true
    }

    override fun createDirectories(path: Path) {
        // NOOP
    }

    override fun copy(input: InputStream, target: Path, vararg options: CopyOption) {
        // NOOP
    }

    override fun createFile(path: Path): Path {
        return mock(Path::class.java)
    }

    override fun getOutputStream(path: Path): OutputStream {
        return mock(OutputStream::class.java)
    }
}