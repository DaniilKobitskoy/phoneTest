package com.mysticism.data.datasource

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class FileDownloadManager(private val context: Context) {

    private val okHttpClient = OkHttpClient()

    fun downloadFile(url: String, destinationFileName: String): File {

        val request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            throw Exception("Failed to download file")
        }

        val file = File(context.cacheDir, destinationFileName)

        response.body?.byteStream()?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return file
    }
}
