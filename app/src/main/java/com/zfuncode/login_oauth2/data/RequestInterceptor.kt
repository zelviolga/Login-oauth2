package com.zfuncode.login_oauth2.data

import android.content.Context
import com.zfuncode.login_oauth2.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Jika token ada di session manager, token sisipkan di request header
        sessionManager.fetchAccessToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}