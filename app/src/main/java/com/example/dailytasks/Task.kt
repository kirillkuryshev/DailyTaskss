package com.example.dailytasks

data class Task(
    val title: String,
    var isCompleted: Boolean,
    val priority: String // Цвет задачи: "red", "yellow", "green"
)