package com.geticons.data.Repository

import android.content.Context
import android.content.LocusId
import com.geticons.API.APIService
import com.geticons.API.NetworkCall
import com.geticons.API.ServiceBuilder
import com.geticons.API.ServiceBuilder.safeApiCall
import com.google.gson.JsonObject
import retrofit2.Response

class MainRepository {

    suspend fun searchicon(
        context: Context,
        term: String,
        count:Int
    ): Response<JsonObject> = ServiceBuilder.apiService.searchIcon(term,count)


    suspend fun getIconSets(

        count:Int,
        after:Int?
    ): Response<JsonObject> = ServiceBuilder.apiService.getIconSets(count,after)

    suspend fun getIconSetDetail(

        id:Int
    ): Response<JsonObject> = ServiceBuilder.apiService.getIconSetDetail(id)

    suspend fun getIcons_IconSet(

        id:Int
    ): Response<JsonObject> = ServiceBuilder.apiService.getIcons_IconSet(id)


    suspend fun getIconDetail(

        id:Int
    ): Response<JsonObject> = ServiceBuilder.apiService.getIconDetail(id)

    suspend fun getAuthorDetail(

        id:Int
    ): Response<JsonObject> = ServiceBuilder.apiService.getAuthorDetail(id)



    suspend fun getAuthorIconSets(

        id:Int
    ): Response<JsonObject> = ServiceBuilder.apiService.getAuthorIconSets(id)


    /* APIService.safeApiCall(context = context,
         call = {
             ApiHelper.apiService.getuserSessionToken(sessionRequest)
         }
     )*/
}