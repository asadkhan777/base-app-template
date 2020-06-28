package com.asadkhan.base_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.asadkhan.base_app.CityDetailsPageShort
import com.asadkhan.base_app.Navigator
import com.asadkhan.base_app.R
import com.asadkhan.global.base.BaseViewHolder
import com.asadkhan.global.domain.city.view.CityItemViewData
import kotlinx.android.synthetic.main.view_holder_city_search_result.view.tv_city_country_search_result
import kotlinx.android.synthetic.main.view_holder_city_search_result.view.tv_city_name_search_result
import kotlinx.android.synthetic.main.view_holder_city_search_result.view.tv_city_urban_area_search_result

class SearchCityAdapter(private val context: Context) : Adapter<BaseViewHolder>() {
  
  private val citiesSearchResultsList = ArrayList<CityItemViewData>()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.view_holder_city_search_result, parent, false)
    return SearchCityViewHolder(view)
  }
  
  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is SearchCityViewHolder) {
      val adapterPosition = holder.adapterPosition
      val cityItemViewData = citiesSearchResultsList[adapterPosition]
      with(holder.itemView) {
        this.setOnClickListener {
          Navigator.navigationLiveData.postValue(CityDetailsPageShort(cityItemViewData))
        }
        tv_city_name_search_result.text = cityItemViewData.getCityName()
        tv_city_urban_area_search_result.text = cityItemViewData.getUrbanAreaName()
        tv_city_country_search_result.text = cityItemViewData.getCountryName()
      }
    }
  }
  
  override fun getItemCount() = citiesSearchResultsList.size
  
  fun updateItems(citiesList: List<CityItemViewData>?) {
    if (citiesList != null && citiesList.isNotEmpty()) {
      citiesSearchResultsList.clear()
      citiesSearchResultsList.addAll(citiesList)
      notifyDataSetChanged()
    }
  }
  
  fun clear() {
    citiesSearchResultsList.clear()
    notifyDataSetChanged()
  }
  
}

class SearchCityViewHolder(view: View) : BaseViewHolder(view)
