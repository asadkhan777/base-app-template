package com.asadkhan.global

import android.app.Application
import timber.log.Timber

/**
 * The base application class responsible for exposing global app instance
 * */
class CityApp : Application() {
  companion object {
    lateinit var instance: CityApp
  }
  
  override fun onCreate() {
    instance = this
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}
