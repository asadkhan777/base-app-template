package com.asadkhan.base_app.screens

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.asadkhan.base_app.R
import com.asadkhan.base_app.adapters.SearchCityAdapter
import com.asadkhan.base_app.viewmodels.CityViewModel
import com.asadkhan.global.base.BaseFragment
import com.asadkhan.global.disableDelayedUntilRelapse
import com.asadkhan.global.hide
import com.asadkhan.global.location.LocationHelper
import com.asadkhan.global.location.REQUEST_CHECK_LOCATION_SETTINGS
import com.asadkhan.global.location.requestLocationSettings
import com.asadkhan.global.setErrorMessage
import com.asadkhan.global.show
import com.asadkhan.global.toast
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.fragment_search_cities.bt_search_submit
import kotlinx.android.synthetic.main.fragment_search_cities.et_city_search
import kotlinx.android.synthetic.main.fragment_search_cities.iv_current_location
import kotlinx.android.synthetic.main.fragment_search_cities.pb_current_location_loading
import kotlinx.android.synthetic.main.fragment_search_cities.rv_search_results
import kotlinx.android.synthetic.main.fragment_search_cities.til_city_search

class SearchCitiesFragment : BaseFragment() {
  
  private val cityViewModel by lazy { viewModelProvider.get(CityViewModel::class.java) }
  private val searchCityResultsAdapter by lazy { SearchCityAdapter(requireActivity()) }
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_search_cities, container, false)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    rv_search_results.apply {
      layoutManager = LinearLayoutManager(requireActivity())
      adapter = searchCityResultsAdapter
    }
    
    cityViewModel.showProgressBar.observe(viewLifecycleOwner, Observer { loading ->
      if (!loading) {
        bt_search_submit.setText(R.string.search)
      }
    })
    
    cityViewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer { citiesList ->
      if (citiesList == null || citiesList.isEmpty()) {
        searchCityResultsAdapter.clear()
      } else {
        searchCityResultsAdapter.updateItems(citiesList)
      }
    })
    
    cityViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
      if (it != null) {
        toast(R.string.unable_to_fetch_cities)
      }
    })
    
    iv_current_location.setOnClickListener {
      iv_current_location.hide()
      iv_current_location.disableDelayedUntilRelapse(4000)
      pb_current_location_loading.show()
      fetchNearbyCitiesByCurrentLocation()
    }
    
    et_city_search.addTextChangedListener { text ->
      if ("$text".length > 0) {
        til_city_search.setErrorMessage("")
      }
    }
    
    bt_search_submit.setOnClickListener {
      val text = "${et_city_search.text}"
      til_city_search.setErrorMessage("")
      when {
        text.isBlank()  -> {
          til_city_search.setErrorMessage("Search query cannot be blank")
        }
        text.length < 2 -> {
          til_city_search.setErrorMessage("Search query must contain at least 2 characters")
        }
        else            -> {
          // Input is validated, now proceed
          bt_search_submit.disableDelayedUntilRelapse(3000)
          bt_search_submit.setText(R.string.searching)
          cityViewModel.searchCityByText(text)
        }
      }
    }
  }
  
  private fun fetchNearbyCitiesByCurrentLocation() {
    val activity = requireActivity()
    val cachedLocation = LocationHelper.lastLocationLiveData.value
    if (cachedLocation != null) {
      cityViewModel.fetchNearestCity(cachedLocation.latitude, cachedLocation.longitude)
    }
    activity.runWithPermissions(ACCESS_COARSE_LOCATION) {
      activity.requestLocationSettings { location ->
        iv_current_location.show()
        iv_current_location.isEnabled = true
        pb_current_location_loading.hide()
        cityViewModel.fetchNearestCity(location.latitude, location.longitude)
      }
    }
  }
  
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      REQUEST_CHECK_LOCATION_SETTINGS -> handleLocationSettingsRequest(resultCode, data)
      else                            -> {
      }
    }
  }
  
  private fun handleLocationSettingsRequest(resultCode: Int, data: Intent?) {
    if (resultCode == AppCompatActivity.RESULT_OK) {
      fetchNearbyCitiesByCurrentLocation()
    } else {
      toast("Please give us permission to locate cities near you")
    }
  }
  
}
