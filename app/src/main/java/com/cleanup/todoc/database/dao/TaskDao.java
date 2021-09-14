package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by lleotraas on 18.
 */
@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<TaskWithProject>> getTasksWithProject();

    @Insert
    long insertTask(Task task);

    @Query("DELETE FROM Task WHERE mId = :taskId")
    int deleteTask(long taskId);
}
