package com.example.dailytasks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TasksActivity : AppCompatActivity() {

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasks: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        // Получаем дату из Intent
        val date = intent.getStringExtra("selectedDate") ?: "Сегодня"
        val tasksTitle: TextView = findViewById(R.id.tasks_title)
        tasksTitle.text = "Задачи на $date"

        // Загружаем задачи
        tasks = loadTasks()

        val recyclerView: RecyclerView = findViewById(R.id.tasks_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = TasksAdapter(tasks)
        recyclerView.adapter = tasksAdapter

        // Обработчик нажатия на кнопку "+"
        val addButton: ImageButton = findViewById(R.id.button_add_task)
        addButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val taskTitle = data?.getStringExtra("task_title") ?: return
            val taskPriority = data.getStringExtra("task_priority") ?: "red"
            val newTask = Task(taskTitle, false, taskPriority)

            tasks.add(newTask)
            tasksAdapter.notifyItemInserted(tasks.size - 1)
        }
    }

    override fun onPause() {
        super.onPause()
        saveTasks() // Сохраняем задачи при выходе из приложения
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("tasks_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Преобразуем список задач в JSON
        val gson = Gson()
        val json = gson.toJson(tasks)

        // Сохраняем JSON в SharedPreferences
        editor.putString("tasks_list", json)
        editor.apply()
    }

    private fun loadTasks(): MutableList<Task> {
        val sharedPreferences = getSharedPreferences("tasks_pref", Context.MODE_PRIVATE)

        // Получаем JSON-строку из SharedPreferences
        val json = sharedPreferences.getString("tasks_list", null)

        // Если данные отсутствуют, возвращаем пустой список
        if (json.isNullOrEmpty()) {
            return mutableListOf()
        }

        // Преобразуем JSON в список объектов Task
        val gson = Gson()
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return gson.fromJson(json, type)
    }
}

