package com.asadkhan.global.database.entities

import androidx.room.Dao
import androidx.room.Query

@Dao
class CityDetailsDao {
  @Query("SELECT * FROM citydetails WHERE geoname_id = ':geoNameId'")
  fun getCityDetailsByID(geoNameId: Int) {
  
  }
}