package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

/**
 * Created by lleotraas on 18.
 */
public class ProjectDataRepository {
    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    // --- GETTER ---
    public LiveData<Project> getProject(long projectId) {
        return this.mProjectDao.getProject(projectId);
    }

}
