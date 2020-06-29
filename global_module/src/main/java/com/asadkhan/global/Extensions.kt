package com.asadkhan.global

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.N
import android.os.Build.VERSION_CODES.O
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import com.asadkhan.global.database.city.CityDetails
import com.asadkhan.global.domain.city.view.CityItemViewData
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

/**
 * Wraps the passed function with a try catch, handles all errors with generic
 * EHM (Error Handling Mechanism); It returns `true` if the function completed
 * successfully & `false` if exception occurred. Only works if block does not return.
 * */
fun tryThis(block: () -> Any?): Boolean {
  return try {
    block()
    true
  } catch (e: Exception) {
    e.printStackTrace()
    false
  }
}

/**
 * Wraps the passed function with a try catch, handles all errors with generic
 * EHM (Error Handling Mechanism). Returns null on failure
 * */
fun <RETURN> tryHere(block: () -> RETURN?): RETURN? {
  try {
    return block()
  } catch (e: Exception) {
    e.printStackTrace()
  }
  return null
}

/**
 * Wraps the passed function with a try catch, handles all errors with generic
 * EHM (Error Handling Mechanism). Returns default (Non-null) value on failure
 * */
fun <RETURN> tryHere(block: () -> RETURN?, default: RETURN): RETURN {
  try {
    return block() ?: default
  } catch (e: Exception) {
    e.printStackTrace()
  }
  return default
}

// Generic

fun Any?.isApiAboveOrEqual(minLevel: Int) = SDK_INT >= minLevel
fun Any?.isOreo() = SDK_INT >= O
fun Any?.isNougat() = SDK_INT >= N
fun Any?.isMarshmellow() = SDK_INT >= M
fun Any?.isLollipop() = SDK_INT >= LOLLIPOP

fun Any?.zeroPaddedNumber(number: Int): String {
  return when {
    number > 9999 -> "$number"     // 10000 & beyond
    number > 999  -> "0$number"    // 1000 to 9999
    number > 99   -> "00$number"   // 100 to 999
    number > 9    -> "000$number"  // 10 to 99
    else          -> "0000$number" // 0 to 9
  }
}


//fun database(): PokedexDatabase = component().database()
//fun pokemonTable(): PokemonDao = component().database().pokemonDao()
val gson: Gson by lazy { GsonBuilder().setPrettyPrinting().setLenient().create() }

fun contex(): Context = BaseApp.app

// Time based

fun Any?.now() = Date().time

//// Profiling
//
//fun Any?.start() = Profiler.start()
//fun Any?.stop(name: String = "Method") = Profiler.stop(name)

// Context based

fun Context?.asyncTost(text: String): Boolean {
  if (this == null) {
    return false
  }
  return tryThis {
    this.let {
      Handler(mainLooper).post {
        val makeText = makeText(applicationContext, text, LENGTH_LONG)
        makeText?.show()
      }
    }
  }
}

fun Context?.launchService(intent: Intent) {
  if (isOreo()) {
    this?.startForegroundService(intent)
  } else {
    this?.startService(intent)
  }
}

fun Context?.isNetworkAvailable(): Boolean {
  this?.let {
    val activeNetworkInfo = it.networkInfo()
    return activeNetworkInfo?.isConnected ?: false
  }
  return false
}

fun Context?.isWifiAvailable(): Boolean {
  this?.let {
    val activeNetworkInfo = it.networkInfo()
    return activeNetworkInfo?.subtype == ConnectivityManager.TYPE_WIFI
  }
  return false
}

val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

fun Any?.toast(resId: Int, duration: Int = LENGTH_LONG) {
  val ctx = BaseApp.app
  val message = ctx.getString(resId)
  toast(message, duration)
}

fun Any?.toast(message: String?, duration: Int = LENGTH_LONG) {
  val ctx = BaseApp.app
  GlobalScope.launch(Dispatchers.Main) {
    handler.postAtFrontOfQueue {
      try {
        makeText(ctx, message, duration).show()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}

fun Activity?.isActive() = this != null && !isFinishing

fun Fragment.toast(message: String?, duration: Int = LENGTH_LONG) {
  try {
    makeText(context, message, duration).show()
  } catch (e: Exception) {
    e.printStackTrace()
  }
}

fun Fragment.isActive() = this.isAdded && this.context != null && this.view != null

@SuppressLint("MissingPermission")
fun Context?.networkInfo(): NetworkInfo? {
  val connectivityManager = this?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
  return connectivityManager.activeNetworkInfo
}

fun Context?.getDrawableId(name: String): Int {
  return this?.resources?.getIdentifier(name, "drawable", this.packageName) ?: -1
}

// View based

fun View?.show(): View? {
  tryThis {
    this?.apply {
      visibility = VISIBLE
    }
  }
  return this
}

fun View?.hide(gone: Boolean = true): View? {
  tryThis {
    this?.apply {
      visibility = if (gone) GONE else INVISIBLE
    }
  }
  return this
}

fun TextInputLayout.setErrorMessage(errorMessage: String) {
  error = errorMessage
  isErrorEnabled = errorMessage.isNotBlank()
}

fun View.disableDelayedUntilRelapse(delayAmount: Long = 3000) {
  CoroutineScope(Dispatchers.Main).launch {
    isEnabled = false
    delay(delayAmount)
    try {
      isEnabled = true
    } catch (e: Exception) {
      Timber.e(e)
    }
  }
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

fun Any?.simpleName() = this?.javaClass?.simpleName ?: "NA"

fun <T> Collection<T>?.isNotNullAndNotEmpty(): Boolean {
  return this != null && this.isNotEmpty()
}

inline fun <reified T : Any> String?.inflate(klass: Class<T>): T? {
  if (isNullOrBlank()) {
    return null
  }
  return try {
    val adapter = gson.getAdapter(klass)
    adapter.fromJson(this)
  } catch (e: Throwable) {
    e.printStackTrace()
    null
  }
}

inline fun <reified T : Any> T.deflate(): String? {
  return try {
    val adapter = gson.getAdapter(T::class.java)
    adapter.toJson(this)
  } catch (e: Throwable) {
    e.printStackTrace()
    null
  }
}

inline fun <reified T : Any> T.deepCopy(): T {
  val adapter = gson.getAdapter(T::class.java)
  return adapter.fromJson(gson.toJson(this)) as T
}

fun CityDetails.getLabelValuePairs(): ArrayList<Pair<String, String>> {
  val pairs = ArrayList<Pair<String, String>>()
  return pairs.apply {
    add("Name" to name)
    add("Province" to province)
    add("Country" to country)
    add("Timezone" to timezone)
    add("Population" to "$population")
  }
}

fun CityItemViewData.getLabelValuePairs(): ArrayList<Pair<String, String>> {
  val pairs = ArrayList<Pair<String, String>>()
  return pairs.apply {
    add("Name" to getCityName())
    add("Province" to getUrbanAreaName())
    add("Country" to getCountryName())
    add("Timezone" to "- - - -")
    add("Population" to "- - - -")
  }
  
}
