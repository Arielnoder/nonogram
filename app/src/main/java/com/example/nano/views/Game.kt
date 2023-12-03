package com.example.nano.views

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
fun NonogramScreen(navController: NavController,repository: com.example.nano.model.boardRepository) {

    var userLayout by remember { mutableStateOf<Int?>(null) }

    TableController().getUserLayout() { layout ->
        userLayout = layout
    }
    println(userLayout)


    userLayout?.let {
        var board = TableController().getRandomBoardByLayout(repository,it)
        var id = board?.id
        if (id != null) {
            TableController().GameBoard(boardRepository = repository,id)
        }


    }
}