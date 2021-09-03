package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lleotraas on 18.
 */
@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Insert
    long insertTask(Task task);

    @Query("DELETE FROM Task WHERE mId = :taskId")
    int deleteTask(long taskId);
}
