package com.asadkhan.global.database.city

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class CityDetailsDao {
  
  @Query("SELECT * FROM $TABLE_CITY WHERE geoname_id = :geoNameId LIMIT 1")
  abstract suspend fun getCityDetailsByID(geoNameId: Int): CityDetails?
  
  @Query("SELECT * FROM $TABLE_CITY WHERE isFavorite")
  abstract suspend fun getFavoriteCities(): List<CityDetails>
  
  @Insert(onConflict = REPLACE)
  abstract suspend fun insertCity(city: CityDetails): Long
  
  @Update(onConflict = REPLACE)
  abstract suspend fun updateCity(city: CityDetails): Int
  
  @Query("SELECT COUNT(:geoNameId) FROM $TABLE_CITY")
  abstract suspend fun cityRowCount(geoNameId: Int): Long
  
  suspend fun doesCityExist(geoNameId: Int): Boolean {
    val cityRowCount = cityRowCount(geoNameId)
    return cityRowCount > 0
  }
}