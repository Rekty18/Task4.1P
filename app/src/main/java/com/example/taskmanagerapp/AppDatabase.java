package com.example.taskmanagerapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.taskmanagerapp.dao.TaskDao;
import com.example.taskmanagerapp.model.Task;

@Database(entities = {Task.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
