package com.mysticism.data.repository

import com.google.gson.Gson
import com.mysticism.data.datasource.FileDownloadManager
import com.mysticism.data.local.ContactDao
import com.mysticism.data.local.PreferencesManager
import android.util.Log
import com.mysticism.data.models.Contacts
import com.mysticism.data.models.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository(
    private val fileDownloadManager: FileDownloadManager,
    private val contactDao: ContactDao,
    private val preferences: PreferencesManager
) {
    private val downloadUrl = "https://drive.google.com/uc?export=download&id=1YvE6Y5-vxVWXrrYyb83ssqMfVUUkndLw"

    suspend fun fetchAndSaveContactsIfNeeded() {
        if (!preferences.isDataDownloaded()) {
            try {
                val jsonFile = withContext(Dispatchers.IO) {
                    fileDownloadManager.downloadFile(downloadUrl, "test_task_mock_data.json")
                }
                val jsonData = jsonFile.readText()
                val contacts = parseJson(jsonData)
                withContext(Dispatchers.IO) {
                    contactDao.insertContacts(contacts)
                }
                preferences.setDataDownloaded(true)
            } catch (e: Exception) {
            }
        } else {
        }
    }

    private fun parseJson(jsonData: String): List<Data> {
        val gson = Gson()
        val contactResponse = gson.fromJson(jsonData, Contacts::class.java)

        contactResponse.data.forEach { contact ->
        }

        return contactResponse.data.map { contact ->
            Data(
                id = contact.id,
                firstName = contact.firstName ?: "Unknown",
                lastName = contact.lastName ?: "Unknown",
                email = contact.email ?: "Unknown",
                gender = contact.gender ?: "Unknown",
                ipAddress = contact.ipAddress ?: "Unknown"
            )
        }
    }
    suspend fun getFavoriteContacts(): List<Data> {
        return contactDao.getFavoriteContacts()
    }

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        contactDao.updateFavoriteStatus(id, isFavorite)
    }
    suspend fun getAllContacts(): List<Data> {
        return withContext(Dispatchers.IO) {
            contactDao.getAllContacts()
        }
    }
}