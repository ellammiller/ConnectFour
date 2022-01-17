package edu.towson.cosc435.vargashernandez.connectfour.database

interface IProfileRepo {
    suspend fun getAll(): List<ProfileItem>
    suspend fun get(): ProfileItem
    suspend fun delete(profileItem: ProfileItem)
    suspend fun add(profileItem: ProfileItem)
    suspend fun update(profileItem: ProfileItem)
}