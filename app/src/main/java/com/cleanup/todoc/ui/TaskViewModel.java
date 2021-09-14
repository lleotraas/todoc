package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

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
    private LiveData<List<Project>> currentProject;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public void init(){
        if(this.currentProject != null){
            return;
        }
        currentProject = projectDataSource.getAllProject();
    }

    // --- FOR PROJECT ---
    public LiveData<List<Project>> getAllProject(){
        return this.currentProject;
    }

    // --- FOR TASK ---
    public LiveData<List<TaskWithProject>> getTasksWithProject(){
        return taskDataSource.getTasksWithProject();
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
}
