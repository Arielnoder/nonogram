package com.example.nano.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nano.controller.TableController

@Composable
fun NonogramScreen(navController: NavController) {
    TableController().DynamicTable(rows = 15, cols = 15)
}