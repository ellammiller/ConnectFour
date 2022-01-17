package edu.towson.cosc435.vargashernandez.connectfour.database

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val _profiles: MutableState<List<ProfileItem>> = mutableStateOf(listOf())
    private val _singleProfile: MutableState<ProfileItem> = mutableStateOf(ProfileItem(-1,"",0,0))
    val profiles: State<List<ProfileItem>> = _profiles

    private val _repository: IProfileRepo = ProfileRepo(app)

    init {
        viewModelScope.launch {
            _profiles.value = _repository.getAll()
        }
    }

    suspend fun getAll(): List<ProfileItem>{
        _profiles.value = _repository.getAll()
        return  _profiles.value
    }

    suspend fun add(profileItem: ProfileItem) {
        viewModelScope.launch {
            _repository.add(profileItem)
            _profiles.value = _repository.getAll()
        }
    }

    suspend fun get(): ProfileItem{
        viewModelScope.launch {
            _singleProfile.value = _repository.get()
        }
        return _singleProfile.value
    }

    suspend fun update(profileItem: ProfileItem) {
        viewModelScope.launch {
            _repository.update(profileItem)
            _profiles.value = _repository.getAll()
        }
    }
}