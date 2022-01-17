package edu.towson.cosc435.vargashernandez.connectfour.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile_db")
data class ProfileItem (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "wins")
    val wins: Int,

    @ColumnInfo(name = "losses")
    val losses: Int
)