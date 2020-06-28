package com.asadkhan.global.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job

open class BaseFragment : Fragment() {
  
  private val parentJob = Job()
  private val defaultCoroutineContext get() = parentJob + Default
  private val ioCoroutineContext get() = parentJob + IO
  private val mainCoroutineContext get() = parentJob + Main
  
  protected val defaultScope by lazy { CoroutineScope(defaultCoroutineContext) }
  protected val ioScope by lazy { CoroutineScope(ioCoroutineContext) }
  protected val mainScope by lazy { CoroutineScope(mainCoroutineContext) }
  
  val viewModelProvider by lazy { ViewModelProvider(requireActivity()) }
}