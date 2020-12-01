package com.example.midexam.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{
    @Query("SELECT * FROM task WHERE stateTask=0")
    fun readToDoDataTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE stateTask=1")
    fun readInProgressDataTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE stateTask=2")
    fun readDoneDataTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task WHERE id=:id")
    fun deleteTask(id:Int?)
}