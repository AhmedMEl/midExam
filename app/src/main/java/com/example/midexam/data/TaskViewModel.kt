package com.example.midexam.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskViewModel(application: Application) : AndroidViewModel(application)  {
     val readToDoData:LiveData<List<Task>>
    val readInProgressData:LiveData<List<Task>>
    val readDoneData:LiveData<List<Task>>

    private val repository:TaskRepository

    init {
        val taskDao=TaskDatabase.getDatabase(application).taskDao()
        repository= TaskRepository(taskDao)
        readToDoData=repository.readToDoDataTasks
        readInProgressData=repository.readInProgressDataTasks
        readDoneData=repository.readDoneDataTasks
    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(task)
        }
    }

}