package com.asadkhan.global.database.city

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_CITY)
data class CityDetails(
    @PrimaryKey
    @ColumnInfo(name = "geoname_id")
    val geonameId: Int, // 5391959
    @ColumnInfo(name = "full_name")
    val fullName: String, // San Francisco, California, United States
    @ColumnInfo(name = "latitude")
    val latitude: Double, // 33.729388
    @ColumnInfo(name = "longitude")
    val longitude: Double, // 73.093146
    @ColumnInfo(name = "name")
    val name: String, // San Francisco
    @ColumnInfo(name = "population")
    val population: Int, // 864816, // San Francisco
    @ColumnInfo(name = "province")
    val province: String, // California
    @ColumnInfo(name = "country")
    val country: String, // United States
    @ColumnInfo(name = "timezone")
    val timezone: String, // America/Los_Angeles
    @ColumnInfo(name = "urban_area")
    val urbanArea: String, // San Francisco Bay Area
    @ColumnInfo(name = "self_url")
    val selfUrl: String, // https://api.teleport.org/api/cities/geonameid:5391959/
    val isFavorite: Boolean
) : Parcelable