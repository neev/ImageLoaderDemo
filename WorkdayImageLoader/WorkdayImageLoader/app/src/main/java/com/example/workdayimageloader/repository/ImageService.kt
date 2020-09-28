package com.example.workdayimageloader.repository

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ImageService {
    @GET
    suspend fun getTrendingImages(@Url imageUrl: String): FeedDataItem
    //@GET
    //suspend fun getSearchImage(@Url searchUrl: String, @Query)
}