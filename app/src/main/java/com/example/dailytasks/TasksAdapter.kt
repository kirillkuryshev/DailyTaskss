package com.example.dailytasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private val tasks: MutableList<Task>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.task_title)
        val taskCheck: ImageView = itemView.findViewById(R.id.task_check)
        val taskSettings: ImageView = itemView.findViewById(R.id.task_settings)
        val taskDelete: ImageView = itemView.findViewById(R.id.task_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.title

        // Устанавливаем правильную иконку
        if (task.isCompleted) {
            holder.taskCheck.setImageResource(R.drawable.ic_check)
        } else {
            holder.taskCheck.setImageResource(R.drawable.ic_uncheck)
        }

        // Устанавливаем слушатель клика на иконку
        holder.taskCheck.setOnClickListener {
            // Инвертируем статус задачи
            task.isCompleted = !task.isCompleted

            // Устанавливаем иконку в зависимости от статуса
            if (task.isCompleted) {
                holder.taskCheck.setImageResource(R.drawable.ic_check)
            } else {
                holder.taskCheck.setImageResource(R.drawable.ic_uncheck)
            }

            // Уведомляем адаптер об изменении элемента
            notifyItemChanged(position)
        }

        // Устанавливаем цвет для приоритета задачи
        when (task.priority) {
            "red" -> holder.taskCheck.setColorFilter(holder.itemView.context.getColor(R.color.red))
            "yellow" -> holder.taskCheck.setColorFilter(holder.itemView.context.getColor(R.color.yellow))
            "green" -> holder.taskCheck.setColorFilter(holder.itemView.context.getColor(R.color.green))
        }

        // Устанавливаем обработчик для удаления задачи
        holder.taskDelete.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }




    }





    override fun getItemCount(): Int = tasks.size
}
