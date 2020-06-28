package com.asadkhan.base_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.asadkhan.base_app.R
import com.asadkhan.global.base.BaseViewHolder
import kotlinx.android.synthetic.main.view_holder_city_details_item.view.tv_detail_label
import kotlinx.android.synthetic.main.view_holder_city_details_item.view.tv_detail_value
import timber.log.Timber

class CityDetailsListAdapter(private val context: Context) : Adapter<BaseViewHolder>() {
  
  private val cityDetailsList = ArrayList<Pair<String, String>>()
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.view_holder_city_details_item, parent, false)
    return CityDetailItemViewHolder(view)
  }
  
  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is CityDetailItemViewHolder) {
      val pair = cityDetailsList[holder.adapterPosition]
      with(holder.itemView) {
        tv_detail_label.text = pair.first
        tv_detail_value.text = pair.second
      }
    }
  }
  
  override fun getItemCount() = cityDetailsList.size
  
  fun updateItems(citiesList: List<Pair<String, String>>?) {
    if (citiesList != null && citiesList.isNotEmpty()) {
      cityDetailsList.clear()
      cityDetailsList.addAll(citiesList)
      notifyDataSetChanged()
    } else {
      Timber.e("Attempting to update adapter with invalid list, please check")
    }
  }
  
  fun clear() {
    cityDetailsList.clear()
    notifyDataSetChanged()
  }
  
}

class CityDetailItemViewHolder(view: View) : BaseViewHolder(view)
