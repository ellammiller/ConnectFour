package edu.towson.cosc435.vargashernandez.connectfour.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.*

@Dao
interface ProfileDao {
    @Query("SELECT * from profile_db")
    suspend fun getAll(): List<ProfileItem>

    @Query("SELECT * from profile_db WHERE username =:username")
    suspend fun getUsername(username: String): ProfileItem

    @Query("SELECT * FROM profile_db ORDER BY id ASC LIMIT 1")
    suspend fun get():ProfileItem

    @Update
    suspend fun update(item: ProfileItem)

    @Insert
    suspend fun insert(item: ProfileItem)

    @Delete
    suspend fun delete(item: ProfileItem)
}

@Database(entities = [ProfileItem::class], version = 3, exportSchema = false)
abstract class ProfileDB : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}