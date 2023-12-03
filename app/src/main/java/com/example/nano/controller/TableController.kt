package com.example.nano.controller

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nano.model.board
import com.example.nano.model.boardRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class TableController {




    @Composable
    fun GameBoard(boardRepository: boardRepository, boardId: Int) {
        val boardData = remember { mutableStateOf<board?>(null) }
        val userSelections = remember { mutableStateListOf<Boolean>() }


        LaunchedEffect(boardId) {
            boardData.value = boardRepository.getBoardById(boardId)
            boardData.value?.flippedSquares?.let {
                userSelections.clear()
                userSelections.addAll(it.map  {false}) // Initialize user selections to match flippedSquares
            }
        }

        val layoutRows = boardData.value?.Layout?.split(";") ?: listOf()
        val rowNumbers = boardData.value?.columnNumbers?.split(",") ?: listOf()
        val columnNumbers = boardData.value?.rowNumbers?.split(",") ?: listOf()

        // Assuming gridSize is defined by the number of columns in the layout
        val gridSize = columnNumbers.size


        val numRows = layoutRows.size - 1
        val numCols = columnNumbers.size

        val numberF = if (numRows > 0 && numCols > 0) minOf(330f / maxOf(numRows, numCols), 40f) else 40f

        val size = numberF/1.8

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(10.dp, 180.dp)
        ) {
            item {
                Row(Modifier.background(Color.Gray)) {
                    // Corner cell
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black))
                            .size(width = (numberF *1.3f).dp, height = (numberF * 1.8f).dp)
                    )

                    // Top row for column numbers
                    columnNumbers.forEach { number ->
                        Box(
                            modifier = Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                                         .size(width = (numberF).dp, height = (numberF * 1.8f).dp)
                                .padding(start = 3.dp)
                        ) {
                            Text(
                                text = number,
                                fontSize = (size).sp
                            )
                        }
                    }
                }


                layoutRows.drop(1).forEachIndexed { rowIndex, row ->
                    Row(Modifier.fillMaxWidth()) {
                        // Side column for row numbers
                        Box(
                            modifier = Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                .size(width = (numberF * 1.3f).dp, height = (numberF).dp)

                                .background(Color.Gray)
                                .padding(start = 3.dp)
                        ) {
                            Text(
                                text = rowNumbers.getOrNull(rowIndex) ?: "",
                                fontSize = size.sp
                            )
                        }

                        // Grid cells
                        row.split(",").drop(1).forEachIndexed { colIndex, _ ->
                            val cellIndex = rowIndex * gridSize + colIndex
                            val isCellFilled = userSelections.getOrElse(cellIndex) { false }
                            Box(
                                modifier = Modifier
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .size((numberF ).dp)
                                    .background(if (isCellFilled) Color.Black else Color.White)
                                    .clickable {
                                        userSelections[cellIndex] = !isCellFilled
                                    }
                            )
                        }
                    }
                }
            }

            item {
                val userSelectionsList = userSelections.toList()
                Button(onClick = {
                    if (userSelectionsList == boardData.value?.flippedSquares) {
                        println("Success")
                    } else {
                        println("Failed")
                    }
                }) {
                    Text("Check")
                }
            }
        }
    }

    fun getRandomBoardByLayout(boardRepository: boardRepository, layout: Int): board? {
        val boards = boardRepository.getBoardByLayout(layout.toString())
        return boards.randomOrNull()
    }






    fun isAdmin(callback: (Boolean) -> Unit ) {
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser
        db.collection("users").document(user?.uid.toString()).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.data?.get("role").toString()
                    if (role == "admin") {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }

    }
    fun getUserLayout(callback: (Int?) -> Unit) {
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser

        db.collection("users").document(user?.uid.toString()).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val layout = document.data?.get("layout").toString().toInt()
                    callback(layout)
                }
            }





    }


    fun setUserLayout(gridSize: Int) {
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser

        // Check if the user is not null
        if (user != null) {
            // Create a map to hold the updated layout data
            val layoutData = hashMapOf("layout" to gridSize)

            // Update the user's document in the 'users' collection
            db.collection("users").document(user.uid).set(layoutData, SetOptions.merge())
                .addOnSuccessListener {
                    // Successfully updated the user's layout
                    Log.d("setUserLayout", "User layout successfully updated to $gridSize")
                }
                .addOnFailureListener { e ->
                    // Handle the error
                    Log.w("setUserLayout", "Error updating user layout", e)
                }
        } else {
            // Handle the case where there is no authenticated user
            Log.w("setUserLayout", "No authenticated user found")
        }
    }



    @Composable
    fun EditableDynamicTable(rows: Int, cols: Int, boardRepository: boardRepository) {
        val tableData = remember {
            mutableStateListOf(
                MutableList(cols + 1) { CellState("", false) },
                *List(rows) { MutableList(cols + 1) { CellState("", false) } }.toTypedArray()
            )
        }

        val numberF = minOf(330f / maxOf(rows, cols), 40f)
        val size = numberF / 2

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(15.dp, 180.dp)
        ) {
            item {
                Row(Modifier.background(Color.Gray)) {
                    // Corner cell
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black))
                            .size(numberF.dp)
                    )

                    // Top row cells for numbers
                    for (colIndex in 1 until tableData[0].size) {
                        Box(
                            modifier = Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                .size(numberF.dp)
                                .padding(start = 3.dp)
                        ) {
                            var topText by remember { mutableStateOf(tableData[0][colIndex].text) }

                            BasicTextField(
                                value = topText,
                                onValueChange = { newValue ->
                                    topText = newValue
                                    tableData[0][colIndex] = CellState(newValue, tableData[0][colIndex].isFilled)
                                },
                                textStyle = TextStyle(fontSize = size.sp),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            itemsIndexed(tableData.drop(1)) { rowIndex, rowData ->
                Row(Modifier.fillMaxWidth()) {
                    // Side column cells for numbers
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black))
                            .size(numberF.dp)
                            .background(Color.Gray)
                            .padding(start = 3.dp)
                    ) {
                        var sideText by remember { mutableStateOf(rowData[0].text) }

                        BasicTextField(
                            value = sideText,
                            onValueChange = { newValue ->
                                sideText = newValue
                                tableData[rowIndex + 1][0] = CellState(newValue, rowData[0].isFilled)
                            },
                            textStyle = TextStyle(fontSize = size.sp),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Grid cells
                    for (colIndex in 1 until rowData.size) {
                        var isCellFilled by remember { mutableStateOf(rowData[colIndex].isFilled) }

                        Box(
                            modifier = Modifier
                                .border(BorderStroke(1.dp, Color.Black))
                                .size(numberF.dp)
                                .clickable {
                                    isCellFilled = !isCellFilled
                                    tableData[rowIndex + 1][colIndex] = CellState(rowData[colIndex].text, isCellFilled)
                                }
                                .background(if (isCellFilled) Color.Black else Color.White)
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        val layout = serializeLayout(tableData)
                        val flippedSquares = getFlippedSquares(tableData)
                        val rowNumbers = getRowNumbers(tableData)
                        val columnNumbers = getColumnNumbers(tableData)

                        CoroutineScope(Dispatchers.IO).launch {
                            boardRepository.insertBoard("Board Name", layout, flippedSquares, rowNumbers, columnNumbers)
                        }
                    },
                    modifier = Modifier.size(100.dp)
                ) {
                    Text(text = "Save Table")
                }
            }

        }
    }

    fun serializeLayout(tableData: List<List<CellState>>): String {
        return tableData.joinToString(separator = ";") { row ->
            row.joinToString(separator = ",") { cell ->
                if (cell.isFilled) "1" else "0"
            }
        }
    }
    fun getFlippedSquares(tableData: List<List<CellState>>): List<Boolean> {
        return tableData.drop(1).flatMap { row ->
            row.drop(1).map { cell ->
                cell.isFilled
            }
        }
    }
    fun getRowNumbers(tableData: List<List<CellState>>): String {
        return tableData.first().drop(1).joinToString(",") { it.text }
    }

    fun getColumnNumbers(tableData: List<List<CellState>>): String {
        return tableData.drop(1).map { it.first().text }.joinToString(",")
    }
    fun printGameBoard(boardRepository: boardRepository, boardId: Int) {
        val board = boardRepository.getBoardById(boardId)

        board?.let {
            // Assuming layout is a string like "1,0,0;0,1,0;0,0,1"
            val rows = it.Layout.split(";")
            val flippedSquares = it.flippedSquares

            println("Board Name: ${it.name}")
            println("Row Numbers: ${it.rowNumbers}")
            println("Column Numbers: ${it.columnNumbers}")
            println("Board Layout:")

            for ((rowIndex, row) in rows.withIndex()) {
                val cells = row.split(",")
                for ((colIndex, cell) in cells.withIndex()) {
                    val cellState = if (flippedSquares[rowIndex * cells.size + colIndex]) "#" else " "
                    print("$cellState ")
                }
                println() // New line after each row
            }
        } ?: println("Board not found")
    }



    data class CellState(val text: String, var isFilled: Boolean)




}






