package com.asadkhan.global

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asadkhan.global.BaseApp.Companion.app

const val MY_CONTACTS_DB = "MY_CONTACTS_DB"

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
    entities = [Any::class],
    version = 1
)
abstract class MyContactsDatabase : RoomDatabase() {
  
  abstract fun contactDetailsDao(): Dao
  
}

val database by lazy {
  Room.databaseBuilder(app, MyContactsDatabase::class.java, MY_CONTACTS_DB)
      .fallbackToDestructiveMigration()
      .allowMainThreadQueries()
      .build()
}