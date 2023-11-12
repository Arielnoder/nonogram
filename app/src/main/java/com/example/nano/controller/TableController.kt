package com.example.nano.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nano.model.TableData

class TableController {
    fun getTableData(): TableData {
        return TableData(5, 5, listOf(
            listOf("","","","",""),
            listOf("","","","",""),
            listOf("","","","",""),
            listOf("","","","",""),
            listOf("","","","","")
        ))
    }





    fun onCellClicked(row: Int, col: Int, isCellClicked : Boolean) {

    }

    @Composable
    fun DynamicTable(rows: Int, cols: Int) {
        val tableData = List(rows) { rowIndex ->
            List(cols) { colIndex ->
                ""
            }
        }

        val numberF = minOf(330f / maxOf(rows, cols), 40f)
        val size = numberF/2

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(15.dp,180.dp)) {
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
}