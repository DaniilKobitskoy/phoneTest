package com.mysticism.domain.usecase

import android.content.Context
import com.mysticism.data.local.ContactDao
import com.mysticism.data.remote.ImageApi
import com.mysticism.data.models.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File

class FetchAndSaveImagesUseCase(
    private val context: Context,
    private val imageApiService: ImageApi,
    private val contactDao: ContactDao
) {

    suspend fun execute(contacts: List<Data>) {
        withContext(Dispatchers.IO) {
            try {
                val images = mutableListOf<File>()
                var downloadSuccessful = true

                repeat(5) { index ->
                    try {
                        val imageResponse: ResponseBody = imageApiService.getImage()
                        val file = File(context.filesDir, "image_$index.jpg")

                        file.outputStream().use { outputStream ->
                            imageResponse.byteStream().use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }

                        images.add(file)
                    } catch (e: Exception) {
                        downloadSuccessful = false
                        e.printStackTrace()
                    }
                }

//                val fallbackImagePath = "android.resource://${context.packageName}/drawable/list_avatar_bg"
//
//                contacts.forEach { contact ->
//                    if (contact.imagePath == null) {
//                        val imagePath = if (downloadSuccessful && images.isNotEmpty()) {
//                            images.random().absolutePath
//                        } else {
//                            fallbackImagePath
//                        }
//                        contact.imagePath = imagePath
//                        contactDao.updateContactImage(contact.id, imagePath)
//                    }
//                }
            } catch (e: Exception) {
                e.printStackTrace()
                val fallbackImagePath = "android.resource://${context.packageName}/drawable/list_avatar_bg"
                contacts.forEach { contact ->
                    if (contact.imagePath == null) {
                        contact.imagePath = fallbackImagePath
                        contactDao.updateContactImage(contact.id, fallbackImagePath)
                    }
                }
            }
        }
    }
}
