package com.asadkhan.global.domain.city.nearby


import android.os.Parcelable
import com.asadkhan.global.domain.city.common.Coordinates
import com.asadkhan.global.domain.city.common.TeleportLinkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityResponseData(
    @SerializedName("_embedded")
    val embeddedNearestCityData: EmbeddedNearestCityData,
    @SerializedName("coordinates")
    val coordinates: Coordinates
) : Parcelable

@Parcelize
data class Cury(
    @SerializedName("href")
    val href: String, // https://developers.teleport.org/api/resources/Location/#!/relations/{rel}/
    @SerializedName("name")
    val name: String, // location
    @SerializedName("templated")
    val templated: Boolean // true
) : Parcelable

@Parcelize
data class EmbeddedNearestCityData(
    @SerializedName("location:nearest-cities")
    val locationNearestCities: List<LocationNearestCity>,
    @SerializedName("location:nearest-urban-areas")
    val locationNearestUrbanAreas: List<LocationNearestUrbanArea>
) : Parcelable

@Parcelize
data class LocationNearestCity(
    @SerializedName("_links")
    val nearbyCityLink: NearbyCityLink,
    @SerializedName("distance_km")
    val distanceKm: Double // 4.703217
) : Parcelable

@Parcelize
data class LocationNearestUrbanArea(
    @SerializedName("_links")
    val nearbyCityLink: NearbyUrbanAreaLink,
    @SerializedName("distance_km")
    val distanceKm: Double // 4.703217
) : Parcelable

@Parcelize
data class NearbyCityLink(
    @SerializedName("location:nearest-city")
    val locationNearestCityLink: TeleportLinkEntity
) : Parcelable

@Parcelize
data class NearbyUrbanAreaLink(
    @SerializedName("location:nearest-urban-area")
    val locationNearestCityLink: TeleportLinkEntity
) : Parcelable

@Parcelize
data class LocationNearestCityLink(
    @SerializedName("href")
    val href: String, // https://api.teleport.org/api/cities/geonameid:1176615/
    @SerializedName("name")
    val name: String // Islamabad
) : Parcelable