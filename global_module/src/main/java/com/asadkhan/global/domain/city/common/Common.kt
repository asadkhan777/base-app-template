package com.asadkhan.global.domain.city.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TeleportLink(
    @SerializedName("href")
    val href: String
) : Parcelable


@Parcelize
data class TeleportLinkEntity(
    @SerializedName("href")
    val href: String,
    @SerializedName("name")
    val name: String
) : Parcelable


@Parcelize
data class Coordinates(
    @SerializedName("geohash")
    val geoHash: String,
    @SerializedName("latlon")
    val latLong: LatLong
) : Parcelable


@Parcelize
data class LatLong(
    @SerializedName("latitude")
    val latitude: Double, // 33.729388
    @SerializedName("longitude")
    val longitude: Double // 73.093146
) : Parcelable