package com.example.a62.data.remote

import com.example.a62.data.model.RemoteNobelResponseRetrofit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NovelPriceApiRetrofit {
    @GET("2.1/nobelPrizes")
    suspend fun getNobelPrices(@Query("limit") limit: Int = 50, @Query("offset") offset: Int = 0): Response<RemoteNobelResponseRetrofit>
}
