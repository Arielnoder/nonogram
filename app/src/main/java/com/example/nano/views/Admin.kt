package com.example.nano.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.nano.controller.TableController
import com.example.nano.model.boardDao
import com.example.nano.model.boardRepository

@Composable
fun Admin(navController: NavController,repository: boardRepository) {
  var userLayout by remember { mutableStateOf<Int?>(null) }

// Assuming TableController is the class where getUserLayout function is defined
  TableController().getUserLayout() { layout ->
    userLayout = layout
  }
  println(userLayout)

  userLayout?.let { TableController().EditableDynamicTable(rows = it, cols = it,repository ) }


}