package com.geticons.API

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("icons/search")
    suspend fun searchIcon(@Query("user_id") user_id: String,
                           @Query("count") count: Int): Response<JsonObject>

    @GET("iconsets")
    suspend fun getIconSets(@Query("count") count: Int,
                            @Query("after") after: Int?): Response<JsonObject>


    @GET("iconsets/{iconset_id}")
    suspend fun getIconSetDetail(@Path("iconset_id") iconset_id: Int): Response<JsonObject>


    @GET("iconsets/{iconset_id}/icons")
    suspend fun getIcons_IconSet(@Path("iconset_id") iconset_id: Int): Response<JsonObject>

    @GET("icons/{icon_id}")
    suspend fun getIconDetail(@Path("icon_id") icon_id: Int): Response<JsonObject>


    @GET("authors/{author_id}")
    suspend fun getAuthorDetail(@Path("author_id") author_id: Int): Response<JsonObject>

    @GET("authors/{author_id}/iconsets")
    suspend fun getAuthorIconSets(@Path("author_id") author_id: Int): Response<JsonObject>
}