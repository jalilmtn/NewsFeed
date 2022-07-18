package com.example.newsfeed.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsfeed.network.model.Result

@Database(entities = [Result::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}