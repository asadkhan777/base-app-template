package com.asadkhan.global.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.asadkhan.global.simpleName
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

open class BaseFragment : Fragment() {
  
  private val parentJob = SupervisorJob()
  private val defaultCoroutineContext get() = parentJob + Default
  private val ioCoroutineContext get() = parentJob + IO
  private val mainCoroutineContext get() = parentJob + Main
  
  protected val defaultScope get() = CoroutineScope(defaultCoroutineContext)
  protected val ioScope get() = CoroutineScope(ioCoroutineContext)
  protected val mainScope get() = CoroutineScope(mainCoroutineContext)
  
  val viewModelProvider by lazy { ViewModelProvider(this) }
  
  override fun onStop() {
    super.onStop()
    parentJob.cancelChildren(CancellationException("Fragment ${simpleName()} is not longer active"))
  }
}