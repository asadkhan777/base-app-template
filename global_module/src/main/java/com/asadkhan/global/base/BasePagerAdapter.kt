package com.asadkhan.global.base

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.asadkhan.global.domain.PostData

class BasePagerAdapter : PagerAdapter() {

    val postData = mutableListOf<PostData>()

    override fun isViewFromObject(view: View, objectInput: Any) = true

    override fun getCount() = -1

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }
}