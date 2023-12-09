package com.example.task.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    var userInfoState by mutableStateOf(UserInfo())
        private set

    fun onNameChange(newValue: String){
        userInfoState = userInfoState.copy(name = newValue)
    }

    fun onLastNameChange(newValue: String){
        userInfoState = userInfoState.copy(lastName = newValue)
    }

    fun onBirthdayChange(newValue: String){
        userInfoState = userInfoState.copy(birthday = newValue)
    }

    fun onCodeChange(newValue: String){
        userInfoState = userInfoState.copy(code = newValue)
    }


}

data class UserInfo(
    val name: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val code: String = "",
)