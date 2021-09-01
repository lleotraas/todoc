package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by lleotraas on 18.
 */
public class TaskViewModel extends ViewModel {

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public void init(long projectId){
        if(this.currentProject != null){
            return;
        }
        currentProject = projectDataSource.getProject(projectId);
    }

    // --- FOR PROJECT ---
    public LiveData<Project> getProject(long projectId){
        return this.currentProject;
    }

    // --- FOR TASK ---
    public LiveData<List<Task>> getTask(long projectId){
        return taskDataSource.getTask(projectId);
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDataSource.getAllTasks();
    }

    public void createTask(Task task){
        executor.execute(()->{
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(()->{
            taskDataSource.deleteTask(taskId);
        });
    }

    public void updateTask(Task task){
        executor.execute(()->{
            taskDataSource.updateTask(task);
        });
    }
}
