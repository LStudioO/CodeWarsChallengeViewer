package com.vstorchevyi.codewars.server

import androidx.test.platform.app.InstrumentationRegistry
import com.vstorchevyi.codewars.runner.TestApplication
import java.io.IOException
import java.io.InputStreamReader

// Reads files from the assets
object FileReader {
    fun readStringFromFile(fileName: String): String {
        try {
            val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as TestApplication).assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}
