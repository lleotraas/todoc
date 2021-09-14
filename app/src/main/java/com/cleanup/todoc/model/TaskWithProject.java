package com.cleanup.todoc.model;






import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by lleotraas on 10.
 */
public class TaskWithProject {
    @Relation(
            parentColumn = "project_id",
            entityColumn = "id",
            entity = Project.class
    )
    public List<Project> mProject;

    @Embedded
    public Task task;
}
