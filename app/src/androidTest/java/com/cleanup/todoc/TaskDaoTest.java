package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.cleanup.todoc.database.ProjectDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by lleotraas on 01.
 */
@RunWith(JUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private ProjectDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID,"test de projet", 000000);
    private static Task NEW_TASK_DEMO = new Task(1, PROJECT_ID, "faire la vaisselle du test", 12);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb()throws InterruptedException{
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                        ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .build();
    }

    @After
    public void closeDb() throws InterruptedException{
        database.close();
    }

    @Test
    public void insertAndGetProject()throws InterruptedException{
        this.database.projectDao().createProject(PROJECT_DEMO);
        List<Project> project = LiveDataTestUtil.getValue(this.database.projectDao().getAllProject());
        assertTrue(project.get(0).getName().equals(PROJECT_DEMO.getName()) && project.get(0).getId() == PROJECT_ID);
    }

    @Test
    public void getTaskSWhenNoTaskInserted()throws InterruptedException{
        //TEST
        List<TaskWithProject> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksWithProject());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetItems() throws InterruptedException{
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_DEMO);

        List<TaskWithProject> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksWithProject());
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void insertAndDeleteTask()throws InterruptedException{
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_DEMO);
        List<TaskWithProject> taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasksWithProject());
        this.database.taskDao().deleteTask(taskAdded.get(0).task.getId());

        //TEST
        List<TaskWithProject> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksWithProject());
        assertTrue(tasks.isEmpty());
    }
}
