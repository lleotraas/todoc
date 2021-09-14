package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by lleotraas on 18.
 */
public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    // --- GETTER ---
    public LiveData<List<TaskWithProject>> getTasksWithProject(){ return this.mTaskDao.getTasksWithProject(); }
    // --- CREATE ---
    public void createTask(Task task){
        mTaskDao.insertTask(task);
    }
    // --- DELETE ---
    public void deleteTask(long taskId){
        mTaskDao.deleteTask(taskId);
    }
}
