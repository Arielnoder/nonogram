package com.example.nano.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nano.controller.TableController

@Composable
fun Admin(navController: NavController) {

    TableController().EditableDynamicTable(rows = 10, cols = 10)


}