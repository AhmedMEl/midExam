package com.example.midexam.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskViewModel(application: Application) : AndroidViewModel(application)  {
     val readAllData:LiveData<List<Task>>
    private val repository:TaskRepository

    init {
        val taskDao=TaskDatabase.getDatabase(application).taskDao()
        repository= TaskRepository(taskDao)
        readAllData=repository.readAllDataTasks
    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTask(task)
        }
    }

}