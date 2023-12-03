package com.example.nano

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {



    object Login : Screen("Login", resourceId = R.string.Login)
    object Register : Screen("Register", resourceId = R.string.Register)
    object NonogramScreen : Screen("NonogramScreen", resourceId = R.string.Game)

    object Home : Screen("Home", resourceId = R.string.Home)


    object Admin : Screen("Admin", resourceId = R.string.Admin)

    object PickLayout : Screen("PickLayout", resourceId = R.string.PickLayout)








}