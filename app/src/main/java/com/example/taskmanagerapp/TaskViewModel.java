package com.example.taskmanagerapp;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import com.example.taskmanagerapp.model.Task;
import com.example.taskmanagerapp.TaskRepository;



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
}
