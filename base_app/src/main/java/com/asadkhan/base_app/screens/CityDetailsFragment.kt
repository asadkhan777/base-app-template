package com.asadkhan.base_app.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.asadkhan.base_app.CityDetailsPage
import com.asadkhan.base_app.Navigator
import com.asadkhan.base_app.R
import com.asadkhan.base_app.adapters.CityDetailsListAdapter
import com.asadkhan.base_app.viewmodels.CityViewModel
import com.asadkhan.global.base.BaseFragment
import com.asadkhan.global.database.city.CityDetails
import com.asadkhan.global.domain.city.view.CityItemViewData
import com.asadkhan.global.getLabelValuePairs
import com.asadkhan.global.toast
import kotlinx.android.synthetic.main.fragment_city_details.rv_city_details_list

class CityDetailsFragment : BaseFragment() {
  
  private val cityViewModel by lazy { viewModelProvider.get(CityViewModel::class.java) }
  private val cityDetailsListAdapter by lazy { CityDetailsListAdapter(requireActivity()) }
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_city_details, container, false)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    rv_city_details_list.apply {
      layoutManager = LinearLayoutManager(requireActivity())
      adapter = cityDetailsListAdapter
    }
    
    cityViewModel.cityDetailsLiveData.observe(viewLifecycleOwner, Observer { details ->
      if (details != null) {
        bindCityDetails(details)
        cityViewModel.citySavedStatusLiveData.postValue(details.isFavorite)
      } else {
        cityViewModel.citySavedStatusLiveData.postValue(null)
      }
    })
    
    when (val currentState = Navigator.navigationLiveData.value) {
      is CityDetailsPage -> bindNewCity(currentState.geoNameID)
      else               -> {
        toast("Unable to fetch city details. Please retry")
      }
    }
  }
  
  override fun onStop() {
    super.onStop()
    Navigator.cityItemViewLiveData.postValue(null)
    cityViewModel.cityDetailsLiveData.postValue(null)
  }
  
  private fun bindNewCity(geoNameID: Int) {
    val cityItemViewData = Navigator.cityItemViewLiveData.value
    if (cityItemViewData != null && geoNameID == cityItemViewData.getGeoCodeID()) {
      bindCityViewData(cityItemViewData)
    }
    cityViewModel.fetchCityDetails(geoNameID)
  }
  
  private fun bindCityDetails(cityDetails: CityDetails?) {
    if (cityDetails != null) {
      val detailsList = cityDetails.getLabelValuePairs()
      cityDetailsListAdapter.updateItems(detailsList)
    }
  }
  
  private fun bindCityViewData(cityItemViewData: CityItemViewData) {
    val detailsList = cityItemViewData.getLabelValuePairs()
    cityDetailsListAdapter.updateItems(detailsList)
  }
  
}

