package com.gmotors.core

import java.io.InputStream
import java.io.OutputStream
import java.nio.file.CopyOption
import java.nio.file.Path

interface FileSystem {
    fun exists(path: Path): Boolean
    fun createDirectories(path: Path)
    fun copy(input: InputStream, target: Path, vararg options: CopyOption)
    fun createFile(path: Path): Path
    fun getOutputStream(path: Path): OutputStream
}