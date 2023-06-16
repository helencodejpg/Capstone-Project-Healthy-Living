package com.example.healthyliving.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.healthyliving.remote.response.UserPreference
import kotlinx.coroutines.launch

class DataStoreViewModel (private val pref: UserPreference) : ViewModel() {

    fun getLoginState(): LiveData<Boolean> {
        return pref.getLoginState().asLiveData()
    }

    fun saveLoginState(loginState: Boolean) {
        viewModelScope.launch {
            pref.saveLoginState(loginState)
        }
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }
    fun getName(): LiveData<String> {
        return pref.getName().asLiveData()
    }
    fun saveName(token: String) {
        viewModelScope.launch {
            pref.saveName(token)
        }
    }
}