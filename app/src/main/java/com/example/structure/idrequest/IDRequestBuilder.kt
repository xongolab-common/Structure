package com.example.structure.idrequest

import android.content.Context
import android.content.Intent
import com.example.structure.util.Constants.BASE_URL
import com.example.structure.util.Pref
import com.example.structure.view.activity.MainActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object IDRequestBuilder {

    private const val NETWORK_CALL_TIMEOUT = 60
    private const val API_URL = BASE_URL

    // API_URL
    const val LANGUAGE_LIST_API: String = "admin/language/list?status=active"


    private var ipRequestService: IDRequestService? = null

    fun getInstance(context: Context): IDRequestService {
        if (ipRequestService == null) {
            val httpClientBuilder = OkHttpClient.Builder()
                .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)

            if (true) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                httpClientBuilder.addInterceptor(loggingInterceptor)
            }

            httpClientBuilder.addInterceptor(UnauthorizedInterceptor(context))

            httpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("code", Pref.getStringValue(Pref.PREF_LANGUAGE, "EN"))
                    .addHeader("authorization", Pref.getStringValue(Pref.PREF_AUTH_TOKEN, ""))
                    .build()
                chain.proceed(newRequest)
            })

            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            ipRequestService = retrofit.create(IDRequestService::class.java)
        }
        return ipRequestService!!
    }


    class UnauthorizedInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)

            if (response.code == 401) {
                // Handle unauthorized error
                handleUnauthorizedResponse(context)
            }

            return response
        }

        private fun handleUnauthorizedResponse(context: Context) {
            // Post a logout event or start login activity directly
            Pref.clearAllPref()
          //  val intent = Intent(context, LoginActivity::class.java)
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)

        }
    }


}