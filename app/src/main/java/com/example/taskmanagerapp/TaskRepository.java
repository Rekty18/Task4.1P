package com.example.taskmanagerapp;

import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;
import com.example.taskmanagerapp.model.Task;
import com.example.taskmanagerapp.dao.TaskDao;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.taskmanagerapp.TaskDatabase;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Context context) {
        TaskDatabase db = TaskDatabase.getDatabase(context);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insert(task);
        });
    }

    // Method to update a task
    public void update(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.update(task);
        });
    }

    // Method to delete a task by ID
    public void delete(int taskId) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.delete(taskId);
        });
    }

    public LiveData<Task> getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }
}
