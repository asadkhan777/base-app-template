package com.asadkhan.global.network

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.asadkhan.global.CityApp
import com.asadkhan.global.inflate
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

open class BaseRepository {
  
  private val defaultMessage = "Something went wrong"
  private val alternateMessage = "Exception in reading error response"
  
  suspend fun <T : Any> makeNetworkCall(call: suspend () -> Response<T>): Result<T> {
    val result = makeSafeNetworkCall(call)
    when (result) {
      is Result.Success -> {
      }
      is Result.Error   -> {
        result.throwable?.printStackTrace()
        Timber.e(result.errorText)
      }
    }
    return result
  }
  
  suspend fun <T : Any> makeSafeNetworkCall(call: suspend () -> Response<T>): Result<T> {
    return try {
      val response = call.invoke()
      return if (response.isSuccessful) {
        Result.Success(response.body()!!)
      } else {
        val errorBody = response.errorBody()
        val errorJsonString = errorBody?.string() ?: defaultErrorJsonString()
        
        var toastMessage = ""
        try {
          val errorResponse = errorJsonString.inflate(ErrorResponse::class.java)
          errorResponse?.message?.let {
            Timber.e("Error message: $it")
            if (it.isNotBlank()) {
              toastMessage = it
            }
          }
        } catch (e: Exception) {
          val alternate = "$alternateMessage due to ${e.message}"
          Timber.e("Alternate Error message: $alternate")
          if (toastMessage.isBlank()) {
            toastMessage = errorJsonString
          }
        }
        GlobalScope.launch(Main) {
          Toast.makeText(CityApp.instance, toastMessage, LENGTH_LONG).show()
        }
        Result.Error(errorJsonString, response.code())
      }
    } catch (e: Exception) {
      Timber.e("Network Call Exception: $e")
      Result.Error("${e.message} caused by ${e.cause?.message ?: "Unknown root cause"}", 800)
    }
  }
  
  private fun defaultErrorJsonString() = """{"message":"$defaultMessage"}""".trimIndent()
}

sealed class Result<out T : Any>(val throwable: Throwable? = null) {
  
  data class Success<out T : Any>(val data: T) : Result<T>()
  
  data class Error(
      val errorMessage: String?, val errorCode: Int, val errorText: String = "Error ${errorCode}: $errorMessage"
  ) : Result<Nothing>(Throwable(errorText)) {
  
  }
}
