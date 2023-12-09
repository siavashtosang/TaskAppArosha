package com.example.task.navigation

sealed class Screen(val route: String)  {
    object MasterScreen: Screen("master_screen")
    object DetailScreen: Screen("detail_screen")
}