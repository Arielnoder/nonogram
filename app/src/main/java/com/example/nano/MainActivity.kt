package com.example.nano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nano.model.boardRepository
import com.example.nano.model.boardRoomDatabase
import com.example.nano.ui.theme.NanoTheme
import com.example.nano.views.Admin
import com.example.nano.views.Home
import com.example.nano.views.Login
import com.example.nano.views.NonogramScreen
import com.example.nano.views.Register
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth;
    val database by lazy { boardRoomDatabase.getDatabase(this) }
    val repository by lazy { boardRepository(database.boardDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContent {
            NanoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(repository = repository)
                }
            }
        }
    }
}

@Composable
fun Main(repository: boardRepository) {
    val navController = rememberNavController()




    NavHost(navController = navController, startDestination = Screen.Admin.route) {
        composable(Screen.Login.route) {
            Login(navController = navController)
        }
        composable(Screen.Register.route) {
            Register(navController = navController)
        }
        composable(Screen.NonogramScreen.route) {
            NonogramScreen(navController = navController,repository = repository)
        }

        composable(Screen.Admin.route) {
            Admin(navController = navController,repository = repository)
        }

        composable(Screen.Home.route) {
            Home(navController = navController)
        }




    }
}























