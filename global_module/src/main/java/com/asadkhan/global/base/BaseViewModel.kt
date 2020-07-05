package com.asadkhan.global.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asadkhan.global.BaseApp
import com.asadkhan.global.simpleName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import java.util.concurrent.CancellationException


open class BaseViewModel : ViewModel() {
  val app by lazy { BaseApp.app }
  
  private val parentJob = Job()
  private val defaultCoroutineContext get() = parentJob + Default
  private val ioCoroutineContext get() = parentJob + IO
  private val mainCoroutineContext get() = parentJob + Main
  
  protected val defaultScope get() = CoroutineScope(defaultCoroutineContext)
  protected val ioScope get() = CoroutineScope(ioCoroutineContext)
  protected val mainScope get() = CoroutineScope(mainCoroutineContext)
  
  val showProgressBar = MutableLiveData<Boolean>()
  val showMessageInSnackBar = MutableLiveData<String>()
  val emptyStateLiveData = MutableLiveData<Boolean>()
  
  open fun cancelAllJobs() {
    parentJob.cancelChildren(CancellationException("Parent ViewModel (${simpleName()}) is cleared: $parentJob"))
  }
  
  override fun onCleared() {
    super.onCleared()
    cancelAllJobs()
  }
}