package com.example.nano.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class boardRepository(private val boardDao: boardDao) {

    val allboards: Flow<List<board>> = boardDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertBoard(name: String, layout: String, flippedSquares: List<Boolean>, rowNumbers: String, columnNumbers: String) {
        val board = board(
            id = 0, // 0 for autoGenerate
            name = name,
            Layout = layout,
            flippedSquares = flippedSquares,
            rowNumbers = rowNumbers,
            columnNumbers = columnNumbers
        )
        boardDao.insert(board)
    }

    // create a function that returns all boards using getAll from the dao

    // create a function that inserts an board using insert from the dao

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getboards() : Flow<List<board>>{
        return allboards
    }



    // get board name by id
    @Suppress("RedundantSuspendModifier")

    @WorkerThread
    fun getboardName(id: Int) : String{
        return boardDao.loadAllByIds(intArrayOf(id))[0].name
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        boardDao.deleteAll()
    }

    // get board by id

    @Suppress("RedundantSuspendModifier")

    @WorkerThread

     fun getBoardById(id: Int): board? {
        return boardDao.getBoardById(id)
    }

    // update board









}