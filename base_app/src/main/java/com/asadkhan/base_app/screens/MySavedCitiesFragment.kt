package com.asadkhan.base_app.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.asadkhan.base_app.R
import com.asadkhan.base_app.adapters.SavedCityAdapter
import com.asadkhan.base_app.viewmodels.CityViewModel
import com.asadkhan.global.base.BaseFragment
import com.asadkhan.global.hide
import com.asadkhan.global.show
import kotlinx.android.synthetic.main.fragment_saved_cities.iv_empty_state
import kotlinx.android.synthetic.main.fragment_saved_cities.rv_saved_cities
import kotlinx.android.synthetic.main.fragment_saved_cities.tv_empty_state

class MySavedCitiesFragment : BaseFragment() {
  
  private val cityViewModel by lazy { viewModelProvider.get(CityViewModel::class.java) }
  private val savedCityAdapter by lazy { SavedCityAdapter(requireActivity()) }
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_saved_cities, container, false)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    rv_saved_cities.apply {
      layoutManager = LinearLayoutManager(requireActivity())
      adapter = savedCityAdapter
    }
    
    cityViewModel.savedCitiesListLiveData.observe(viewLifecycleOwner, Observer { citiesList ->
      if (citiesList == null || citiesList.isEmpty()) {
        savedCityAdapter.clear()
        
        rv_saved_cities.hide()
        iv_empty_state.show()
        tv_empty_state.show()
      } else {
        savedCityAdapter.updateItems(citiesList)
        
        rv_saved_cities.show()
        iv_empty_state.hide()
        tv_empty_state.hide()
      }
    })
    
    cityViewModel.fetchSavedCities()
  }
  
}