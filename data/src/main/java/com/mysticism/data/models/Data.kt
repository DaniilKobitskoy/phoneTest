package com.mysticism.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "contacts")
data class Data(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") @SerializedName("first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") @SerializedName("last_name") val lastName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("gender") val gender: String?,
    @ColumnInfo(name = "ip_address") @SerializedName("ip_address") val ipAddress: String?,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false,
    @ColumnInfo(name = "image_path") var imagePath: String? = null // Добавьте путь к изображению
)