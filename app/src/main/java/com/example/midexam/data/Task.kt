package com.example.midexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(@PrimaryKey(autoGenerate = true) var id:Int,
                var titleTask: String,
                var detailsTask:String,
                var dateTask: String,
                var stateTask:Int)