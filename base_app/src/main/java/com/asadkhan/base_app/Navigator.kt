package com.asadkhan.base_app

import androidx.lifecycle.MutableLiveData
import com.asadkhan.global.base.MutableMemoizedLiveData
import com.asadkhan.global.domain.city.view.CityItemViewData

object Navigator {
  val cityItemViewLiveData = MutableLiveData<CityItemViewData>()
  val navigationLiveData = MutableMemoizedLiveData<PageNavigationEvent>()
}