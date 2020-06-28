package com.asadkhan.base_app

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.asadkhan.base_app.viewmodels.CityViewModel
import com.asadkhan.global.base.BaseActivity
import com.asadkhan.global.hide
import com.asadkhan.global.isActive
import com.asadkhan.global.show
import kotlinx.android.synthetic.main.activity_my_city_main.iv_back_button
import kotlinx.android.synthetic.main.activity_my_city_main.tv_action_item
import kotlinx.android.synthetic.main.activity_my_city_main.tv_page_title
import timber.log.Timber

class MyCityMainActivity : BaseActivity() {
  
  private val navigator: NavController by lazy { findNavController(R.id.nav_host_fragment_main) }
  
  private val cityViewModel by lazy { ViewModelProvider(this).get(CityViewModel::class.java) }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_city_main)
    
    navigator.setGraph(R.navigation.nav_graph_my_cities)
    
    tv_action_item.setOnClickListener {
      when (val page = navigationLiveData().value) {
        is SavedCitiesPage  -> navigationLiveData().postValue(SearchCitiesPage())
        is SearchCitiesPage -> Timber.e("404: No action found ")
        is CityDetailsPage  -> cityViewModel.toggleCityFavouriteValue(page.geoNameID)
        null                -> Timber.e("Invalid selection")
      }
    }
    
    iv_back_button.setOnClickListener {
      backPressHandler()
    }
    
    cityViewModel.citySavedStatusLiveData.observe(this, Observer { isCitySaved ->
      if (isCitySaved == true) {
        tv_action_item.setText(R.string.remove_city)
      } else {
        tv_action_item.setText(R.string.add_city)
      }
    })
    
    navigationLiveData().observe(this, Observer { pageEvent ->
      handleNavEvent(pageEvent)
    })
    navigationLiveData().postValue(SavedCitiesPage())
  }
  
  private fun handleNavEvent(pageEvent: PageNavigationEvent?) {
    val bundle = Bundle()
    val previousData = navigationLiveData().previousData
    when (pageEvent) {
      is SavedCitiesPage  -> {
        iv_back_button.hide()
        tv_action_item.show()
        tv_action_item.setText(R.string.search)
        tv_page_title.setText(R.string.my_cities)
        if (previousData !is SavedCitiesPage) {
          navigator.navigate(R.id.nav_MySavedCitiesFragment, bundle)
        }
      }
      is SearchCitiesPage -> {
        iv_back_button.show()
        tv_action_item.hide()
        tv_page_title.setText(R.string.search)
        if (previousData !is SearchCitiesPage) {
          navigator.navigate(R.id.nav_SearchCitiesFragment, bundle)
        }
      }
      is CityDetailsPage  -> {
        iv_back_button.show()
        tv_action_item.show()
        tv_page_title.setText(R.string.details)
        tv_action_item.setText(R.string.add_city)
        navigator.navigate(R.id.nav_CityDetailsFragment, bundle)
      }
    }
  }
  
  
  private fun backPressHandler() {
    val navigationLiveData = navigationLiveData()
    val currentPage = navigationLiveData.value
    val previousPage = navigationLiveData.previousData
    if (currentPage is SavedCitiesPage || previousPage == null) {
      finish()
    } else if (currentPage is SearchCitiesPage) {
      val savedCitiesPage = SavedCitiesPage()
      navigationLiveData.value = savedCitiesPage
      cityViewModel.searchResultLiveData.postValue(arrayListOf())
      handleNavEvent(savedCitiesPage)
    } else {
      navigationLiveData.value = previousPage
      handleNavEvent(previousPage)
    }
  }
  
  override fun onBackPressed() {
    backPressHandler()
  }
  
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    val fragment = currentMainFragment()
    if (fragment != null && fragment.isActive()) {
      fragment.onActivityResult(requestCode, resultCode, intent)
    }
  }
  
  private fun currentMainFragment() = getCurrentFragment(R.id.nav_host_fragment_main)
  
  private fun navigationLiveData() = Navigator.navigationLiveData
}
