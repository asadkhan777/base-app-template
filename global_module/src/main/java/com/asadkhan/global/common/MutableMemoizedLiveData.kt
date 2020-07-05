package com.asadkhan.global.common

import androidx.lifecycle.MutableLiveData

class MutableMemoizedLiveData<DATA> : MutableLiveData<DATA>() {
  
  var previousData: DATA? = null
  
  override fun setValue(newValue: DATA?) {
    previousData = this.value
    super.setValue(newValue)
  }
  
  override fun postValue(newValue: DATA) {
    previousData = this.value
    super.postValue(newValue)
  }
  
}