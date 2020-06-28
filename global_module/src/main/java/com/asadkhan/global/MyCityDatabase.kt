package com.asadkhan.global

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asadkhan.global.database.city.CityDetails
import com.asadkhan.global.database.city.CityDetailsDao
import com.asadkhan.global.database.city.MY_CITY_DB

/**
 * Reasons for choosing Room as the persistence library of choice:
 * - RoomDB is much more expressive and yet has concise syntax
 * - Sits on top of SQLiteDB so it gains from the performance SQLite brings
 * - It allows for easily extending, modifying, and manipulating the app database
 * - Based on code-generation, not reflection or ORM type manipulation
 * - Greatly reduces boilerplate code, less chances of buggy modules
 * - Brings compile-time verification of SQL queries, as well as syntax highlighting
 * - Integrates well with other chosen app components (eg. LiveData, Coroutines, etc)
 * */
@Database(
    entities = [CityDetails::class],
    version = 1
)
abstract class MyCityDatabase : RoomDatabase() {
  
  abstract fun cityDetailsDao(): CityDetailsDao
  
}

val database by lazy {
  Room.databaseBuilder(CityApp.instance, MyCityDatabase::class.java, MY_CITY_DB)
      .fallbackToDestructiveMigration()
      .allowMainThreadQueries()
      .build()
}