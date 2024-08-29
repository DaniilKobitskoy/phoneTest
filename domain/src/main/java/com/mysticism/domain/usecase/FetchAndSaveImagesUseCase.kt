package com.mysticism.domain.usecase

import android.content.Context
import com.mysticism.data.local.ContactDao
import com.mysticism.data.remote.ImageApi
import com.mysticism.data.models.Data
import com.mysticism.domain.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

class FetchAndSaveImagesUseCase(
    private val context: Context,
    private val imageApiService: ImageApi,
    private val contactDao: ContactDao
) {

    private val standardImageResourceId = R.raw.list_avatar_bg
    suspend fun execute(contacts: List<Data>) {
        withContext(Dispatchers.IO) {
            try {
                val images = mutableListOf<File>()
                val standardImageFile = createStandardImageFile()

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
                        standardImageFile.let { images.add(it) }
                    }
                }

                val imageIndexes = images.indices.toMutableList()
                imageIndexes.shuffle()

                val contactImageMap = contacts.mapIndexed { index, contact ->
                    val imageFile = images[imageIndexes[index % images.size]]
                    contact.imagePath = imageFile.absolutePath
                    contact.id to contact.imagePath
                }.toMap()

                contactImageMap.forEach { (contactId, imagePath) ->
                    contactDao.updateContactImage(contactId, imagePath!!)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createStandardImageFile(): File {
        val standardImageFile = File(context.filesDir, "list_avatar_bg.jpg")
        if (!standardImageFile.exists()) {
            context.resources.openRawResource(standardImageResourceId).use { inputStream ->
                FileOutputStream(standardImageFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        return standardImageFile
    }
}
