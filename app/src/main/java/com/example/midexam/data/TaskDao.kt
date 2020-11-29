package com.example.midexam.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{
    @Query("SELECT * FROM task")
    fun readAllDataTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task:Task)
}