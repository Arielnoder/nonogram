package com.example.nano.views

import androidx.compose.runtime.Composable
import com.example.nano.controller.TableController

@Composable
fun NonogramScreen() {
    TableController().DynamicTable(rows = 5, cols = 5)
}