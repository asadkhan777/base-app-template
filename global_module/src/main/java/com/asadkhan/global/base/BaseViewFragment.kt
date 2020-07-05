package com.asadkhan.global.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

abstract class BaseViewFragment : Fragment() {
  
  abstract val layoutId: Int
  
  private val parentJob = SupervisorJob()
  private val defaultCoroutineContext get() = parentJob + Default
  private val ioCoroutineContext get() = parentJob + IO
  private val mainCoroutineContext get() = parentJob + Main
  
  protected val defaultScope get() = CoroutineScope(defaultCoroutineContext)
  protected val ioScope get() = CoroutineScope(ioCoroutineContext)
  protected val mainScope get() = CoroutineScope(mainCoroutineContext)
  
  val viewModelProvider by lazy { ViewModelProvider(this) }
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(layoutId, container, false)
  }
  
  override fun onStop() {
    super.onStop()
    parentJob.cancelChildren(CancellationException("Fragment ${simpleName()} is not longer active"))
  }
}