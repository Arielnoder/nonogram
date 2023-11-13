package com.example.nano.controller

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nano.model.TableData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class TableController {
    fun getTableData(): TableData {
        return TableData(
            5, 5, listOf(
                listOf("", "", "", "", ""),
                listOf("", "", "", "", ""),
                listOf("", "", "", "", ""),
                listOf("", "", "", "", ""),
                listOf("", "", "", "", "")
            )
        )
    }


    fun onCellClicked(row: Int, col: Int, isCellClicked: Boolean) {

    }

    @Composable
    fun DynamicTable(rows: Int, cols: Int) {
        val tableData = List(rows) { rowIndex ->
            List(cols) { colIndex ->
                ""
            }
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
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .width(numberF.dp)
                            .height(numberF.dp)
                            .padding(start = 3.dp)

                    ) {
                        Text(
                            text = "",
                            fontSize = size.sp
                        )
                    }
                    for (i in 1..cols) {
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width((numberF).dp)
                                .height(numberF.dp)
                                .padding(start = 3.dp)

                        ) {
                            Text(
                                text = i.toString(),
                                fontSize = size.sp


                            )
                        }
                    }
                }
            }

            itemsIndexed(tableData) { rowIndex, rowData ->
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .width(numberF.dp)
                            .height(numberF.dp)
                            .background(Color.Gray)
                            .padding(start = 3.dp)
                    ) {
                        Text(
                            text = (rowIndex + 1).toString(),
                            fontSize = size.sp
                        )
                    }

                    for (i in rowData.indices) {
                        var isCellFilled by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width(numberF.dp)
                                .height(numberF.dp)
                                .clickable {
                                    isCellFilled = !isCellFilled
                                }
                        ) {
                            Text(
                                text = rowData[i],
                                fontSize = size.sp,

                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(if (isCellFilled) Color.Black else Color.White)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
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

    @Composable
    fun EditableDynamicTable(rows: Int, cols: Int) {

        val tableData = remember {
            mutableStateListOf(
                MutableList(cols) { "" },
                *List(rows) { MutableList(cols) { "" } }.toTypedArray()
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
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .width(numberF.dp)
                            .height(numberF.dp)
                    ) {
                        Text(
                            text = "",
                            fontSize = size.sp
                        )
                    }

                    for (i in 0..cols) {
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width((numberF).dp)
                                .height(numberF.dp)
                                .padding(start = 3.dp)
                        ) {
                            var topText by remember { mutableStateOf("") }

                            BasicTextField(
                                value = topText,
                                onValueChange = {
                                    topText = it
                                    tableData[0][i] = it
                                },
                                textStyle = TextStyle(fontSize = size.sp),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            itemsIndexed(tableData) { rowIndex, rowData ->
                if (rowIndex > 0) {
                    Row(Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width(numberF.dp)
                                .height(numberF.dp)
                                .background(Color.Gray)
                        ) {
                            var sideText by remember { mutableStateOf("") }

                            BasicTextField(
                                value = sideText,
                                onValueChange = {


                                    sideText = it
                                    tableData[0][rowIndex - 1 ] = it

                                },

                                textStyle = TextStyle(fontSize = size.sp),

                                modifier = Modifier.fillMaxSize()

                            )

                        }


                        for (i in rowData.indices) {
                            var isCellFilled by remember { mutableStateOf(false) }

                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Black)
                                    .width(numberF.dp)
                                    .height(numberF.dp)
                                    .clickable {
                                        isCellFilled = !isCellFilled
                                    }
                            ) {
                                Text(
                                    text = rowData[i],
                                    fontSize = size.sp,

                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(if (isCellFilled) Color.Black else Color.White)
                                        .padding(8.dp)
                                )


                            }


                        }

                    }


                }


            }

                item {
                    Button(
                        onClick = {
                            // Log and store the numbers in the top row and first column
                            logAndStoreNumbers(tableData)
                        },
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Text(text = "Save Table")
                    }
                }




        }



    }

    fun logAndStoreNumbers(tableData: List<MutableList<String>>) {
        val topRowNumbers = tableData[0].joinToString(separator = " ")
        val firstColumnNumbers = tableData.drop(0).map { it[0] }.joinToString(separator = " ")

        // Log the numbers
        println("Top Row Numbers: $topRowNumbers")
        println("First Column Numbers: $firstColumnNumbers")

        // TODO: Implement the storage mechanism (e.g., store in a database)
    }




}






