package com.example.nano.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nano.controller.TableController
import com.example.nano.model.boardDao
import com.example.nano.model.boardRepository

@Composable
fun Admin(navController: NavController,repository: boardRepository) {

//   TableController().EditableDynamicTable(rows = 15, cols = 15,repository )
  TableController().GameBoard(boardRepository = repository)


}