package com.example.dailytasks

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var selectedDate: String = "Сегодня" // Значение по умолчанию
    private val EDIT_TASK_REQUEST_CODE = 100 // Код запроса для редактирования задачи

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonDatePicker: Button = findViewById(R.id.button_date_picker)
        val buttonShowTasks: Button = findViewById(R.id.show_tasks_button)



        // Обработчик для кнопки "Выбрать дату"
        buttonDatePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    Toast.makeText(this, "Выбрана дата: $selectedDate", Toast.LENGTH_SHORT).show()
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Обработчик для кнопки "Показать задачи"
        buttonShowTasks.setOnClickListener {
            val intent = Intent(this, TasksActivity::class.java)
            intent.putExtra("selectedDate", selectedDate) // Передаем выбранную дату
            startActivity(intent)
        }
    }

    /**
     * Метод для открытия окна редактирования задачи
     */
    fun openEditTaskActivity(taskName: String, isTaskCompleted: Boolean) {
        val intent = Intent(this, EditTaskActivity::class.java).apply {
            putExtra("TASK_NAME", taskName) // Передаем название задачи
            putExtra("TASK_COMPLETED", isTaskCompleted) // Передаем статус задачи
        }
        startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
    }

    /**
     * Обработка результата из окна редактирования задачи
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedTaskName = data?.getStringExtra("UPDATED_TASK_NAME")
            val updatedTaskCompleted = data?.getBooleanExtra("UPDATED_TASK_COMPLETED", false)

            // Здесь вы можете обновить задачу в списке
            if (updatedTaskName != null) {
                Toast.makeText(
                    this,
                    "Задача обновлена: $updatedTaskName, Выполнена: $updatedTaskCompleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
