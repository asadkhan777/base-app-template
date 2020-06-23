package com.asadkhan.global.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.asadkhan.global.AbstractApplication.Companion.component

abstract class BaseViewHolder<DATA>(

        val ctx: Context = component().applicationContext,
        val v: View,
        var data: DATA? = null

) : RecyclerView.ViewHolder(v) {

    // No initialization in constructor
    // Data will be bound in the onBind() callback

    fun bindData(newData: DATA?) {
        if (newData == null) {
            error("Data in ViewHolder cannot be null! ViewHolder: ${javaClass.simpleName}")
        }
        this.data = newData
        onBind(data)
    }

    abstract fun onBind(newData: DATA?)

}
