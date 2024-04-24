package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import java.util.ArrayList;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import com.example.taskmanagerapp.model.Task;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private FloatingActionButton fabAddTask;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTaskList().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Log.d("MainActivity", "Tasks updated: " + tasks.size());  // Debugging log
                adapter.setTasks(tasks);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensuring data is refreshed when returning to this Activity
        if (taskViewModel != null) {
            taskViewModel.getTaskList().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(List<Task> tasks) {
                    Log.d("MainActivity", "Resuming and updating tasks: " + tasks.size());  // Debugging log
                    adapter.setTasks(tasks);
                }
            });
        }
    }
    @Override
    public void onTaskClick(int position) {
        // Edit task
        Task taskToEdit = adapter.getTasks().get(position);
        Intent editIntent = new Intent(MainActivity.this, AddEditTaskActivity.class);
        editIntent.putExtra("task_id", taskToEdit.getId());  // Pass task ID to EditActivity
        startActivity(editIntent);
    }

    @Override
    public void onTaskLongClick(int position) {
        // Delete task
        Task taskToDelete = adapter.getTasks().get(position);
        taskViewModel.delete(taskToDelete.getId()); // Pass task ID to delete method
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }

}

