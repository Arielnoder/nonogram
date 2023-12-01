package com.example.nano.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(Converters::class)
interface boardDao {
    @Query("SELECT * FROM board_table")
    fun getAll(): Flow<List<board>>

    @Query("SELECT * FROM board_table WHERE id IN (:boardIds)")
    fun loadAllByIds(boardIds: IntArray): List<board>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg boards: board)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(board: board)

    @Query("DELETE FROM board_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM board_table WHERE id = :id")
     fun getBoardById(id: Int): board?






}