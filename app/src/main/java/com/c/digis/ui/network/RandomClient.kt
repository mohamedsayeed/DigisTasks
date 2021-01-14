package com.c.digis.ui.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RandomClient {

    companion object {
        private const val BASE_URL = "http://51.195.89.92:6000/"
         private val client = buildClient()
        private val retrofit = buildRetrofit(client, BASE_URL)

        fun getRetrofit(): Retrofit {
            return retrofit
        }

        fun <T> createService(service: Class<T>): T {
            return retrofit.create(service)
        }


        private fun buildClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        var request = chain.request()
                        val builder = request.newBuilder()
                          request = builder.build()
                        return chain.proceed(request)
                    }
                }).apply {
                    ignoreAllSSLErrors()
                }
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
            return builder.build()
        }

        private fun buildRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
            val naiveTrustManager = object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            }

            val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
                val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
                init(null, trustAllCerts, SecureRandom())
            }.socketFactory

            sslSocketFactory(insecureSocketFactory, naiveTrustManager)
            hostnameVerifier(HostnameVerifier { _, _ -> true })
            return this
        }
    }
}