package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.ProjectDatabase;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lleotraas on 18.
 */
public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context){
        ProjectDatabase database = ProjectDatabase.getInstance(context);
        return  new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context){
        ProjectDatabase database = ProjectDatabase.getInstance(context);
        return  new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProject, dataSourceTask,executor);
    }
}
