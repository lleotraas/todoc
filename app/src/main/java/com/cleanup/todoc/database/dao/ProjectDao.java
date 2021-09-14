package com.cleanup.todoc.database.dao;

/**
 * Created by lleotraas on 18.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void createProject(Project project);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProject();
}
