package com.example.dailytasks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class EditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val editTaskTitle: EditText = findViewById(R.id.edit_task_title)
        val radioRed: RadioButton = findViewById(R.id.radio_red)
        val radioYellow: RadioButton = findViewById(R.id.radio_yellow)
        val radioGreen: RadioButton = findViewById(R.id.radio_green)
        val buttonSaveTask: Button = findViewById(R.id.button_save_task)

        // Получение данных из Intent
        val taskTitle = intent.getStringExtra("task_title") ?: ""
        val taskPriority = intent.getStringExtra("task_priority") ?: "red"

        // Устанавливаем данные в поля
        editTaskTitle.setText(taskTitle)
        when (taskPriority) {
            "red" -> radioRed.isChecked = true
            "yellow" -> radioYellow.isChecked = true
            "green" -> radioGreen.isChecked = true
        }

        buttonSaveTask.setOnClickListener {
            val updatedTitle = editTaskTitle.text.toString()
            val updatedPriority = when {
                radioRed.isChecked -> "red"
                radioYellow.isChecked -> "yellow"
                radioGreen.isChecked -> "green"
                else -> "red"
            }

            val resultIntent = Intent().apply {
                putExtra("updated_title", updatedTitle)
                putExtra("updated_priority", updatedPriority)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
