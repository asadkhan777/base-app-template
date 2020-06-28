package com.asadkhan.global.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asadkhan.global.CityApp
import com.asadkhan.global.MyCityDatabase
import com.asadkhan.global.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext


open class BaseViewModel : ViewModel() {
  val app by lazy { CityApp.instance }
  
  private val parentJob = Job()
  private val defaultCoroutineContext: CoroutineContext get() = parentJob + Default
  private val ioCoroutineContext: CoroutineContext get() = parentJob + IO
  private val mainCoroutineContext: CoroutineContext get() = parentJob + Main
  
  protected val defaultScope = CoroutineScope(defaultCoroutineContext)
  protected val ioScope = CoroutineScope(ioCoroutineContext)
  protected val mainScope = CoroutineScope(mainCoroutineContext)
  
  val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
  val showMessageInSnackBar: MutableLiveData<String> = MutableLiveData()
  val emptyStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
  
  open fun cancelAllJobs() {
    parentJob.cancelChildren(CancellationException("Parent ViewModel (${javaClass.simpleName}) is cleared: $parentJob"))
  }
}