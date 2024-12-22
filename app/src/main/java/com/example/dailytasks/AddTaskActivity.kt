package com.example.dailytasks

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    private var selectedPriority: String = "red"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val backButton: ImageView = findViewById(R.id.button_back)
        backButton.setOnClickListener { onBackPressed() }

        val taskInput: EditText = findViewById(R.id.task_input)
        val checkboxIcon: ImageView = findViewById(R.id.checkbox_icon)
        val addButton: Button = findViewById(R.id.add_task_button)

        checkboxIcon.setOnClickListener {
            when (selectedPriority) {
                "red" -> {
                    selectedPriority = "yellow"
                    checkboxIcon.setColorFilter(getColor(R.color.yellow))
                }
                "yellow" -> {
                    selectedPriority = "green"
                    checkboxIcon.setColorFilter(getColor(R.color.green))
                }
                "green" -> {
                    selectedPriority = "red"
                    checkboxIcon.setColorFilter(getColor(R.color.red))
                }
            }
        }

        addButton.setOnClickListener {
            val taskTitle = taskInput.text.toString().trim()
            if (taskTitle.isNotEmpty()) {
                val resultIntent = intent.apply {
                    putExtra("task_title", taskTitle)
                    putExtra("task_priority", selectedPriority)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                taskInput.error = "Введите название задачи"
            }
        }
    }
}
