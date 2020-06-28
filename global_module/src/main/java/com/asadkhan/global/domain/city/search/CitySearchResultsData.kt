package com.asadkhan.global.domain.city.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CitySearchResultsData(
    @SerializedName("_embedded")
    val embeddedCitySearchData: EmbeddedCitySearchData,
    @SerializedName("count")
    val count: Int // 1
) : Parcelable


@Parcelize
data class EmbeddedCitySearchData(
    @SerializedName("city:search-results")
    val citySearchResults: List<CitySearchResult>
) : Parcelable


@Parcelize
data class CitySearchResult(
    @SerializedName("_links")
    val cityItemLink: CityItemLink,
    @SerializedName("matching_alternate_names")
    val matchingAlternateNames: List<MatchingAlternateName>,
    @SerializedName("matching_full_name")
    val matchingFullName: String // Mumbai, Maharashtra, India
) : Parcelable


@Parcelize
data class CityItemLink(
    @SerializedName("city:item")
    val cityItem: CityItem
) : Parcelable


@Parcelize
data class MatchingAlternateName(
    @SerializedName("name")
    val name: String // mumbai
) : Parcelable


@Parcelize
data class CityItem(
    @SerializedName("href")
    val href: String // https://api.teleport.org/api/cities/geonameid:1275339/
) : Parcelable