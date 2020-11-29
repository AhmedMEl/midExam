package com.example.midexam.data

import androidx.lifecycle.LiveData

class TaskRepository(private var taskDao: TaskDao) {
    val readAllDataTasks:LiveData<List<Task>> = taskDao.readAllDataTasks()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }
}