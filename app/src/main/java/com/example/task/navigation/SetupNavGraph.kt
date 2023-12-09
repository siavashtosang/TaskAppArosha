package com.example.task.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.task.Screen.DetailScreen
import com.example.task.Screen.MasterScreen
import com.example.task.data.TaskViewModel

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    val viewModel: TaskViewModel = viewModel()
    val activity = (LocalContext.current as? Activity)
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    val savedValue = sharedPref.all
    val route = if (savedValue.isNotEmpty()){
        Screen.DetailScreen.route
    }else{
        Screen.MasterScreen.route
    }
    NavHost(
        navController = navHostController, startDestination = route ,
        modifier = Modifier.statusBarsPadding()
    ) {
        composable(route = Screen.MasterScreen.route) {
            MasterScreen(
                navigation = navHostController,
                userInfo = viewModel.userInfoState,
                onNameChange = viewModel::onNameChange,
                onLastNameChange = viewModel::onLastNameChange,
                onBirthdayChange = viewModel::onBirthdayChange,
                onCodeChange = viewModel::onCodeChange,
                sharedPref,
            )
        }

        composable(route = Screen.DetailScreen.route) {
            DetailScreen(navigation = navHostController, onClear = { sharedPref.edit().clear().apply()}, sharedPref,)
        }
    }
}