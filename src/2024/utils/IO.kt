package utils

import java.io.File
import java.nio.file.Path

class IO {
    companion object {
        fun readFileLines(pathname: String): List<String> {
            val rootPath = Path.of("").toAbsolutePath();
            val inputStream = File("$rootPath/$pathname").inputStream()
            val lineList = mutableListOf<String>()

            inputStream.bufferedReader().forEachLine { lineList.add(it) }

            return lineList;
        }
    }
}