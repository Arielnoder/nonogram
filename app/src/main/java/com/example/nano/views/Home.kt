package com.example.nano.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nano.controller.TableController
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {

    var isAdmin by remember { mutableStateOf(false) }
    TableController().isAdmin() { data ->
        isAdmin = data
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Nonogram") },

                )
        },
        content = {
            Column(
                modifier = Modifier.padding(120.dp),
                verticalArrangement = Arrangement.Center,


                ) {
                Button(onClick = { navController.navigate("Game") }) {
                    Text(text = "New Game")
                }
                Spacer(modifier = Modifier.height(50.dp))
                if (isAdmin) {

                    Button(onClick = { navController.navigate("Admin") }) {

                        Text(text = "Admin Page")
                    }
                }
            }
        }
    )
    Box(
        contentAlignment = Alignment.TopEnd,
    ) {
        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            navController.navigate("Login")
        }) {
            Text(text = "Sign Out")
        }

    }
}
