package com.asadkhan.global

import android.app.Application

/**
 * The base application class responsible for exposing Dagger injection
 * */
abstract class CityApp : Application() {
  companion object {
    lateinit var instance: CityApp
  }
  
  override fun onCreate() {
    super.onCreate()
    instance = this
  }
}
