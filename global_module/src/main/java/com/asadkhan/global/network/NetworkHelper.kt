package com.asadkhan.global.network

import com.asadkhan.global.BaseApp.Companion.app
import com.asadkhan.global.BuildConfig.DEBUG
import com.asadkhan.global.gson
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager.Period.FOREVER
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

const val timeout = 60L

val logger by lazy { getHttpLoggingInterceptor() }

fun getHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
  level = if (DEBUG) BODY else HEADERS
}


val chucker by lazy { getChuckerInterceptor() }

fun getChuckerInterceptor(): ChuckerInterceptor {
  // Create the Collector
  val chuckerCollector = ChuckerCollector(
      context = app,
      // Toggles visibility of the push notification
      showNotification = true,
      // Allows to customize the retention period of collected data
      retentionPeriod = FOREVER
  )
  
  // Create the Interceptor
  return ChuckerInterceptor(
      context = app,
      // The previously created Collector
      collector = chuckerCollector
  )
}

private fun initializeOkHttp(): OkHttpClient {
  return with(OkHttpClient.Builder()) {
    dispatcher(Dispatcher())
    writeTimeout(timeout, SECONDS)
    readTimeout(timeout, SECONDS)
    connectTimeout(timeout, SECONDS)
    callTimeout(timeout, SECONDS)
    addInterceptor(chucker)
    addInterceptor(logger)
  }.build()
}

val okHttpClient by lazy(::initializeOkHttp)


fun initializeRetrofit(): Retrofit {
  return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(okHttpClient)
      .build()
}

val retrofit by lazy { initializeRetrofit() }

val contactService by lazy { retrofit.create(ContactService::class.java) }
