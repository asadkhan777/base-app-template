package com.asadkhan.global.base

import `in`.shadowfax.gandalf.RiderApp
import `in`.shadowfax.gandalf.database.DbSingleton
import `in`.shadowfax.gandalf.database.RoomDb
import `in`.shadowfax.gandalf.networkingKT.ApiFactory
import `in`.shadowfax.gandalf.networkingKT.SfxRepository
import `in`.shadowfax.gandalf.preferences.RiderInfoSharedPreferences
import `in`.shadowfax.gandalf.utils.DateTimeUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
  val parentJob = Job()
  private val defaultCoroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
  private val ioCoroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
  protected val mainCoroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
  
  protected val defaultScope = CoroutineScope(defaultCoroutineContext)
  protected val ioScope = CoroutineScope(ioCoroutineContext)
  protected val mainScope = CoroutineScope(mainCoroutineContext)
  protected val repository: SfxRepository = SfxRepository(ApiFactory.getInstance().getClient())
  protected val repositoryWithoutRetry: SfxRepository = SfxRepository(ApiFactory.getInstance().getNoRetryClient())
  protected val repositoryWithoutAuth = SfxRepository(ApiFactory.getInstance().getNoAuthClient())
  
  val app by lazy { RiderApp.getInstance() }
  val bus by lazy { EventBus.getDefault() }
  val prefs by lazy { RiderInfoSharedPreferences.getInstance() }
  
  val roomDb = RoomDb.getRoomDb()
  val sqliteDb = DbSingleton.getInstance()
  val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
  val showMessageInSnackBar: MutableLiveData<String> = MutableLiveData()
  val emptyStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
  val serverSimpleDateFormat: SimpleDateFormat = DateTimeUtils.getServerSimpleDateFormat()
  
  open fun cancelAllJobs() {
    parentJob.cancelChildren(CancellationException("Parent ViewModel (${javaClass.simpleName}) is cleared: $parentJob"))
  }
}