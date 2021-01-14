package com.c.digis.ui.network

import com.c.digis.ui.data.RandomValues
import retrofit2.Call
import retrofit2.http.GET

interface RandomApi {
    @GET("random")
    fun getRandomValues(): Call<RandomValues>

}