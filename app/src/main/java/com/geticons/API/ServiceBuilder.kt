package com.geticons.API

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.geticons.Util.ConstantHelper
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HttpsURLConnection

object ServiceBuilder {

    val baseurl="https://api.iconfinder.com/v4/"
    lateinit var apiService: APIService
    lateinit var context: Context
    private val client = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val reqest=chain.request().newBuilder().addHeader("Authorization", "Bearer "+ConstantHelper.APIKEY).build()
                return chain.proceed(reqest)
            }

        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())


        .client(client)
        .build()

    init {
        makeService()
    }

    private fun makeService() {
        apiService = retrofit.create(APIService::class.java)
    }

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

    suspend fun <T> safeApiCall(
        context: Context,
        call: suspend () -> retrofit2.Response<T>
    ) {
        this.context=context


        return

        }

}
