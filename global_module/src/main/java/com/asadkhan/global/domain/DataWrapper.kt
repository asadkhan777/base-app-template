package com.asadkhan.global.domain

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DataWrapper(
        @SerializedName("data")
        val `data`: List<PostData>
) : Parcelable
