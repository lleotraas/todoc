package com.cleanup.todoc.model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.database.TodocDatabase;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lleotraas on 07.
 */
@RunWith(AndroidJUnit4.class)
public class TaskTest extends TestCase {

    // FOR DATA
    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long FIRST_PROJECT_ID = 1;
    private static long SECOND_PROJECT_ID = 2;
    private static long THIRD_PROJECT_ID = 3;

    private static Project FIRST_PROJECT_DEMO = new Project(FIRST_PROJECT_ID,"Premier test de projet", 000000);
    private static Project SECOND_PROJECT_DEMO = new Project(SECOND_PROJECT_ID,"Deuxième test de projet", 000000);
    private static Project THIRD_PROJECT_DEMO = new Project(THIRD_PROJECT_ID,"Troisième test de projet", 000000);

    final Task TASK_DEMO_1 = new Task(1, 1, "Tartampion task 1", 123);
    final Task TASK_DEMO_2 = new Task(2, 2, "Lucidia task 2", 124);
    final Task TASK_DEMO_3 = new Task(3, 3, "Circus task 3", 125);

    private List<TaskWithProject> taskWithProject;

    private TaskWithProject TASK_WITH_PROJECT_DEMO_1;
    private TaskWithProject TASK_WITH_PROJECT_DEMO_2;
    private TaskWithProject TASK_WITH_PROJECT_DEMO_3;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb()throws InterruptedException{
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDao().createProject(FIRST_PROJECT_DEMO);
        this.database.projectDao().createProject(SECOND_PROJECT_DEMO);
        this.database.projectDao().createProject(THIRD_PROJECT_DEMO);
        this.database.taskDao().createTask(TASK_DEMO_1);
        this.database.taskDao().createTask(TASK_DEMO_2);
        this.database.taskDao().createTask(TASK_DEMO_3);

        taskWithProject = LiveDataTestUtil.getValue(this.database.taskDao().getTasksWithProject());

        TASK_WITH_PROJECT_DEMO_1 = taskWithProject.get(0);
        TASK_WITH_PROJECT_DEMO_2 = taskWithProject.get(1);
        TASK_WITH_PROJECT_DEMO_3 = taskWithProject.get(2);
    }

    @After
    public void closeDb() throws InterruptedException{
        database.close();
        taskWithProject.clear();
    }

    @Test
    public void test_tasks_projectId_should_return_project_id_with_success() throws InterruptedException{
        List<Project> allProjects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProject());

        assertEquals(allProjects.get(0).getId(), taskWithProject.get(0).task.getProjectId() & taskWithProject.get(0).mProject.get(0).getId(), taskWithProject.get(0).task.getProjectId());
        assertEquals(allProjects.get(1).getId(), taskWithProject.get(1).task.getProjectId() & taskWithProject.get(1).mProject.get(0).getId(), taskWithProject.get(1).task.getProjectId());
        assertEquals(allProjects.get(2).getId(), taskWithProject.get(2).task.getProjectId() & taskWithProject.get(2).mProject.get(0).getId(), taskWithProject.get(2).task.getProjectId());
    }

    @Test
    public void test_az_comparator() throws InterruptedException {

        final ArrayList<TaskWithProject> taskWithProjects = new ArrayList<>();

        taskWithProjects.add(taskWithProject.get(0));
        taskWithProjects.add(taskWithProject.get(1));
        taskWithProjects.add(taskWithProject.get(2));


        Collections.sort(taskWithProjects, new Task.TaskAZComparator());

        assertSame(taskWithProjects.get(0), TASK_WITH_PROJECT_DEMO_3);
        assertSame(taskWithProjects.get(1), TASK_WITH_PROJECT_DEMO_2);
        assertSame(taskWithProjects.get(2), TASK_WITH_PROJECT_DEMO_1);
    }

    @Test
    public void test_za_comparator() {

        final ArrayList<TaskWithProject> taskWithProjects = new ArrayList<>();

        taskWithProjects.add(taskWithProject.get(0));
        taskWithProjects.add(taskWithProject.get(1));
        taskWithProjects.add(taskWithProject.get(2));

        Collections.sort(taskWithProjects, new Task.TaskZAComparator());

        assertSame(taskWithProjects.get(0), TASK_WITH_PROJECT_DEMO_1);
        assertSame(taskWithProjects.get(1), TASK_WITH_PROJECT_DEMO_2);
        assertSame(taskWithProjects.get(2), TASK_WITH_PROJECT_DEMO_3);
    }

    @Test
    public void test_recent_comparator() {

        final ArrayList<TaskWithProject> taskWithProjects = new ArrayList<>();

        taskWithProjects.add(taskWithProject.get(0));
        taskWithProjects.add(taskWithProject.get(1));
        taskWithProjects.add(taskWithProject.get(2));
        Collections.sort(taskWithProjects, new Task.TaskRecentComparator());

        assertSame(taskWithProjects.get(0), TASK_WITH_PROJECT_DEMO_3);
        assertSame(taskWithProjects.get(1), TASK_WITH_PROJECT_DEMO_2);
        assertSame(taskWithProjects.get(2), TASK_WITH_PROJECT_DEMO_1);
    }

    @Test
    public void test_old_comparator() {

        final ArrayList<TaskWithProject> taskWithProjects = new ArrayList<>();

        taskWithProjects.add(taskWithProject.get(0));
        taskWithProjects.add(taskWithProject.get(1));
        taskWithProjects.add(taskWithProject.get(2));
        Collections.sort(taskWithProjects, new Task.TaskOldComparator());

        assertSame(taskWithProjects.get(0), TASK_WITH_PROJECT_DEMO_1);
        assertSame(taskWithProjects.get(1), TASK_WITH_PROJECT_DEMO_2);
        assertSame(taskWithProjects.get(2), TASK_WITH_PROJECT_DEMO_3);
    }
}