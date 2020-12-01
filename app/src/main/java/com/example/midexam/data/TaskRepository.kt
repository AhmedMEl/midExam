package com.example.midexam.data

import androidx.lifecycle.LiveData

class TaskRepository(private var taskDao: TaskDao) {
    val readToDoDataTasks:LiveData<List<Task>> = taskDao.readToDoDataTasks()
    val readInProgressDataTasks:LiveData<List<Task>> = taskDao.readInProgressDataTasks()
    val readDoneDataTasks:LiveData<List<Task>> = taskDao.readDoneDataTasks()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
}