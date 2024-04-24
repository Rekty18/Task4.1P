package com.example.taskmanagerapp;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.taskmanagerapp.model.Task;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private LiveData<List<Task>> allTasks;
    private TaskRepository taskRepository;

    public TaskViewModel(Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getTaskList() {
        return allTasks;
    }

    // Method to insert a task
    public void insert(Task task) {
        taskRepository.insert(task);
    }

    // Method to update a task
    public void update(Task task) {
        taskRepository.update(task);
    }

    // Method to delete a task by ID
    public void delete(int taskId) {
        taskRepository.delete(taskId);
    }

    // Method to retrieve a task by ID
    public LiveData<Task> getTaskById(int taskId) {
        return taskRepository.getTaskById(taskId);
    }
}

