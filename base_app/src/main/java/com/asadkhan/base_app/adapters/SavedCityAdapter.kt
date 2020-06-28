package com.asadkhan.base_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.asadkhan.base_app.CityDetailsPage
import com.asadkhan.base_app.Navigator
import com.asadkhan.base_app.R
import com.asadkhan.global.base.BaseViewHolder
import com.asadkhan.global.database.city.CityDetails
import kotlinx.android.synthetic.main.view_holder_saved_city.view.tv_saved_city_name

class SavedCityAdapter(private val context: Context) : Adapter<BaseViewHolder>() {
  
  private val savedCitiesList = ArrayList<CityDetails>()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.view_holder_saved_city, parent, false)
    return SavedCityViewHolder(view)
  }
  
  override fun getItemCount() = savedCitiesList.size
  
  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is SavedCityViewHolder) {
      val adapterPosition = holder.adapterPosition
      val cityDetails = savedCitiesList[adapterPosition]
      with(holder.itemView) {
        this.setOnClickListener {
          Navigator.navigationLiveData.postValue(CityDetailsPage(cityDetails.geonameId))
        }
        tv_saved_city_name.text = cityDetails.name
      }
    }
  }
  
  fun updateItems(citiesList: List<CityDetails>?) {
    if (citiesList != null && citiesList.isNotEmpty()) {
      savedCitiesList.clear()
      savedCitiesList.addAll(citiesList)
      notifyDataSetChanged()
    }
  }
  
  fun clear() {
    savedCitiesList.clear()
    notifyDataSetChanged()
  }
  
}

class SavedCityViewHolder(itemView: View) : BaseViewHolder(itemView)

