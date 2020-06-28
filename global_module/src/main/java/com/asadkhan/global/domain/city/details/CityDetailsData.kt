package com.asadkhan.global.domain.city.details


import android.os.Parcelable
import com.asadkhan.global.domain.city.common.Coordinates
import com.asadkhan.global.domain.city.common.TeleportLink
import com.asadkhan.global.domain.city.common.TeleportLinkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CityDetailsData(
    @SerializedName("_links")
    val cityDetailLinks: CityDetailLinks,
    @SerializedName("full_name")
    val fullName: String, // San Francisco, California, United States
    @SerializedName("geoname_id")
    val geonameId: Int, // 5391959
    @SerializedName("location")
    val location: Coordinates,
    @SerializedName("name")
    val name: String, // San Francisco
    @SerializedName("population")
    val population: Int, // 864816
    val isFavorite: Boolean = false // 864816
) : Parcelable

@Parcelize
data class CityDetailLinks(
    @SerializedName("city:admin1_division")
    val cityAdmin1Division: TeleportLinkEntity,
    @SerializedName("city:alternate-names")
    val cityAlternateNames: TeleportLink,
    @SerializedName("city:country")
    val cityCountry: TeleportLinkEntity,
    @SerializedName("city:timezone")
    val cityTimezone: TeleportLinkEntity,
    @SerializedName("city:urban_area")
    val cityUrbanArea: TeleportLinkEntity?,
    @SerializedName("self")
    val selfCityLink: TeleportLink
) : Parcelable

@Parcelize
data class CityAdmin1Division(
    @SerializedName("href")
    val href: String, // https://api.teleport.org/api/countries/iso_alpha2:US/admin1_divisions/geonames:CA/
    @SerializedName("name")
    val name: String // California
) : Parcelable

@Parcelize
data class CityAlternateNames(
    @SerializedName("href")
    val href: String // https://api.teleport.org/api/cities/geonameid:5391959/alternate_names/
) : Parcelable

@Parcelize
data class CityCountry(
    @SerializedName("href")
    val href: String, // https://api.teleport.org/api/countries/iso_alpha2:US/
    @SerializedName("name")
    val name: String // United States
) : Parcelable

@Parcelize
data class CityTimezone(
    @SerializedName("href")
    val href: String, // https://api.teleport.org/api/timezones/iana:America%2FLos_Angeles/
    @SerializedName("name")
    val name: String // America/Los_Angeles
) : Parcelable

@Parcelize
data class CityUrbanArea(
    @SerializedName("href")
    val href: String, // https://api.teleport.org/api/urban_areas/slug:san-francisco-bay-area/
    @SerializedName("name")
    val name: String // San Francisco Bay Area
) : Parcelable

@Parcelize
data class SelfCityLink(
    @SerializedName("href")
    val href: String // https://api.teleport.org/api/cities/geonameid:5391959/
) : Parcelable

