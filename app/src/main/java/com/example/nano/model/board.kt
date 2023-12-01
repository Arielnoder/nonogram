package com.example.nano.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "board_table")
data class board(
 @PrimaryKey(autoGenerate = true) val id: Int = 0,
 val name: String,
 val Layout: String,
 val flippedSquares: List<Boolean>,
 val rowNumbers: String,
 val columnNumbers: String
)
