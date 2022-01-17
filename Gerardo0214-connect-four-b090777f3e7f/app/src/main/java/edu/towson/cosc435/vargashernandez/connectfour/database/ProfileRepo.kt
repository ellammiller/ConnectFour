package edu.towson.cosc435.vargashernandez.connectfour.database

import android.app.Application
import androidx.room.Room

class ProfileRepo(app: Application) : IProfileRepo {
    private val db: ProfileDB
    private var _profiles: List<ProfileItem> = listOf()

    //private val _repository: IProfileRepo = ProfileRepo(app)

    init {
        db = Room.databaseBuilder(
            app,
            ProfileDB::class.java,
            "profile_db"
        ).fallbackToDestructiveMigration().build()
    }

    override suspend fun getAll(): List<ProfileItem> {
        return db.profileDao().getAll()
    }

    override suspend fun get(): ProfileItem{
        return db.profileDao().get()
    }

    override suspend fun delete(profileItem: ProfileItem) {
        db.profileDao().delete(profileItem)
    }

    override suspend fun add(profileItem: ProfileItem) {
        db.profileDao().insert(profileItem)
    }

    override suspend fun update(profileItem: ProfileItem) {
        db.profileDao().update(profileItem)
    }
}