package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by lleotraas on 18.
 */
public class ProjectDataRepository {
    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    // --- GETTER ---
    public LiveData<List<Project>> getAllProject() {
        return this.mProjectDao.getAllProject();
    }
    public  LiveData<Project> getProject(long id){
        return this.mProjectDao.getProject(id);
    }

}
