package com.example.newsfeed.db

import androidx.room.*
import com.example.newsfeed.network.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM Result")
    fun getAll(): Flow<List<Result>>

    @Insert
    fun insertAll(users: List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: Result)

    @Delete
    fun delete(user: Result)

}