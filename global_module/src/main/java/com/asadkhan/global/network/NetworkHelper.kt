package com.asadkhan.global.network

import com.asadkhan.global.CityApp
import com.asadkhan.global.gson
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager.Period.FOREVER
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

const val timeout = 60L

val logger: HttpLoggingInterceptor by lazy { getHttpLoggingInterceptor() }

fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
  return HttpLoggingInterceptor().apply {
    level = BODY
  }
}

val chucker by lazy { getChuckerInterceptor() }

fun getChuckerInterceptor(): ChuckerInterceptor {
  // Create the Collector
  val chuckerCollector = ChuckerCollector(
      context = CityApp.instance,
      // Toggles visibility of the push notification
      showNotification = true,
      // Allows to customize the retention period of collected data
      retentionPeriod = FOREVER
  )
  
  // Create the Interceptor
  return ChuckerInterceptor(
      context = CityApp.instance,
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


val okHttpClient: OkHttpClient by lazy(::initializeOkHttp)

fun initializeRetrofit(): Retrofit {
  return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(okHttpClient)
      .build()
}

val retrofit: Retrofit by lazy { initializeRetrofit() }

val teleportService: TeleportService by lazy { retrofit.create(TeleportService::class.java) }
