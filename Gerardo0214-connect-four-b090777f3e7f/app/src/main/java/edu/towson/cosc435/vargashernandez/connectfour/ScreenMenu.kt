package edu.towson.cosc435.vargashernandez.connectfour

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

sealed class ScreenMenu(
    val route: String,
    val title: String
)
{
    object Home : ScreenMenu("home", "Home")
    object MultiplayerScreen : ScreenMenu("local play screen", "Local Play Screen")
    object OnlineScreen : ScreenMenu("onlinescreen", "Online Screen")
    object Settings : ScreenMenu("settings", "Settings")
    object UsernameScreen : ScreenMenu("usernamescreen", "Profile")
}