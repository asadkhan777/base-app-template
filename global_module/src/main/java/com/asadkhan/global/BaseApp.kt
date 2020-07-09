package com.asadkhan.global

import android.app.Application
import com.asadkhan.global.BuildConfig.DEBUG
import timber.log.Timber

/**
 * The base application class responsible for exposing global app instance
 * */
class BaseApp : Application() {
  companion object {
    lateinit var app: BaseApp
  }
  
  override fun onCreate() {
    super.onCreate()
    app = this
    if (DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}
