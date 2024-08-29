package com.mysticism.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ImageApi {
    @GET("200")
    suspend fun getImage(): ResponseBody
}
