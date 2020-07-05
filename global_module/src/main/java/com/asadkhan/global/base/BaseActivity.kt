package com.asadkhan.global.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.asadkhan.global.Constants
import com.asadkhan.global.simpleName
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

open class BaseActivity : AppCompatActivity() {
  
  private val parentJob = SupervisorJob()
  private val defaultCoroutineContext get() = parentJob + Default
  private val ioCoroutineContext get() = parentJob + IO
  private val mainCoroutineContext get() = parentJob + Main
  
  protected val defaultScope get() = CoroutineScope(defaultCoroutineContext)
  protected val ioScope get() = CoroutineScope(ioCoroutineContext)
  protected val mainScope get() = CoroutineScope(mainCoroutineContext)
  
  val viewModelProvider by lazy { ViewModelProvider(this) }
  
  val answerToEverything = Constants.answerToEverything()
  
  fun getCurrentFragment(containerId: Int): BaseViewFragment? {
    val fragment = supportFragmentManager.findFragmentById(containerId)
    val fragmentPresent = fragment != null && fragment.childFragmentManager.fragments.size > 0
    return if (fragment != null && fragmentPresent) {
      fragment.childFragmentManager.fragments[0] as BaseViewFragment
    } else {
      null
    }
  }
  
  override fun onStop() {
    super.onStop()
    parentJob.cancelChildren(CancellationException("Activity ${simpleName()} is not longer active"))
  }
}