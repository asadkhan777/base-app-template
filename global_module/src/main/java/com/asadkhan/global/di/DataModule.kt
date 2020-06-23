package com.asadkhan.global.di

import android.app.Application
import androidx.annotation.NonNull
import com.asadkhan.global.BuildConfig
import com.asadkhan.global.network.ApiService
import com.asadkhan.global.network.NetworkManager
import com.asadkhan.global.network.NetworkService
import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers.io
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.createWithScheduler
import java.io.File
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
class DataModule() {

    constructor(baseUrl: String) : this() {
        BASE_URL = baseUrl
    }

    @Module
    companion object {

        @Deprecated("Please convert to value extracted from property file to avoid security issues")
        private const val CLIENT_ID = "5cb87048a21bf92"
        private const val CACHE = "Cache-Control"
        private const val SHARED_PREFERENCE_NAME = "instakram_app_preferences"

        @JvmStatic
        var BASE_URL = "https://api.imgur.com/3/"

        val FORMAT_ONE = "yyyy-MM-dd'T'HH:mm:ss"

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun logInterceptor(): Interceptor {
            return HttpLoggingInterceptor().setLevel(BODY)
        }

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun networkCache(app: Application): Cache {
            //setup cache
            val httpCacheDirectory = File(app.cacheDir, "responses")
            val cacheSize = 200 * 1024 * 1024L // 200 MiB
            return Cache(httpCacheDirectory, cacheSize)
        }

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun okHttpClient(
                cache: Cache,
                loggingInterceptor: Interceptor
        ): OkHttpClient {

            return OkHttpClient.Builder()
                    .addNetworkInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(cacheControlInterceptor())
                    .addNetworkInterceptor(authInterceptor())
                    .cache(cache)
                    .connectTimeout(180, SECONDS)
                    .readTimeout(180, SECONDS)
                    .writeTimeout(180, SECONDS)
                    .build()
        }

        private fun cacheControlInterceptor(): Interceptor {
            return Interceptor {
                val response = it.proceed(it.request())
                val maxAge = if (BuildConfig.DEBUG) {
                    60 * 60 * 24 * 30 * 12 // One year, yeah. You read that right.
                } else {
                    60 * 60 * 24 * 30 * 12 // One year, yeah. You read that right.
                }
                return@Interceptor response
                        .newBuilder()
                        .header(CACHE, "public, max-age=$maxAge")
                        .build()
            }
        }

        private fun authInterceptor(): Interceptor {
            return Interceptor { chain ->
                val newRequest = chain
                        .request().newBuilder().addHeader("Authorization", "Client-ID $CLIENT_ID")
                        .build()
                chain.proceed(newRequest)
            }
        }

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun retrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(createWithScheduler(io())).client(okHttpClient)
                    .build()
        }

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun gson(): Gson {
            return GsonBuilder().setDateFormat(FORMAT_ONE).setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).setLenient()
                    .setPrettyPrinting().create()
        }

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun apiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

        @NonNull
        @Provides
        @Singleton
        @JvmStatic
        fun networkService(networkManager: NetworkManager): NetworkService {
            return networkManager
        }


    }
}
