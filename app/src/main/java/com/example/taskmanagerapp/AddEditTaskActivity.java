package com.example.taskmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class AddEditTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextDueDate;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTextTitle = findViewById(R.id.editTextTaskTitle);
        editTextDescription = findViewById(R.id.editTextTaskDescription);
        editTextDueDate = findViewById(R.id.editTextTaskDueDate);
        buttonSave = findViewById(R.id.buttonSaveTask);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please insert title, description and due date", Toast.LENGTH_SHORT).show();
            return;
        }


        finish();
    }
}
