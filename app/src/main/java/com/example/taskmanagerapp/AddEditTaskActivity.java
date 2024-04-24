package com.example.taskmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.taskmanagerapp.model.Task;

public class AddEditTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextDueDate;
    private Button buttonSave;
    private TaskViewModel taskViewModel;
    private int taskId = -1; // Default to -1 to signify a new task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        editTextTitle = findViewById(R.id.editTextTaskTitle);
        editTextDescription = findViewById(R.id.editTextTaskDescription);
        editTextDueDate = findViewById(R.id.editTextTaskDueDate);
        buttonSave = findViewById(R.id.buttonSaveTask);

        // Check if we are editing an existing task
        if (getIntent().hasExtra("task_id")) {
            taskId = getIntent().getIntExtra("task_id", -1);
            // Observe the LiveData<Task> object
            taskViewModel.getTaskById(taskId).observe(this, new Observer<Task>() {
                @Override
                public void onChanged(Task task) {
                    if (task != null) {
                        // Populate EditText fields with task details
                        editTextTitle.setText(task.getTitle());
                        editTextDescription.setText(task.getDescription());
                        editTextDueDate.setText(task.getDueDate());
                    }
                }
            });
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        // Retrieve task details from EditText fields
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        // Validate input fields
        if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Task object
        Task task = new Task(title, description, dueDate);
        task.setId(taskId); // Set task ID

        // Insert or update task based on task ID
        if (taskId != -1) {
            taskViewModel.update(task);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            taskViewModel.insert(task);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        }

        finish(); // Close activity
    }
}
