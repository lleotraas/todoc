package com.cleanup.todoc.model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.ProjectDatabase;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Created by lleotraas on 07.
 */
@RunWith(AndroidJUnit4.class)
public class TaskTest extends TestCase {

    // FOR DATA
    private ProjectDatabase database;

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

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb()throws InterruptedException{
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ProjectDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDao().createProject(FIRST_PROJECT_DEMO);
        this.database.projectDao().createProject(SECOND_PROJECT_DEMO);
        this.database.projectDao().createProject(THIRD_PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_1);
        this.database.taskDao().insertTask(TASK_DEMO_2);
        this.database.taskDao().insertTask(TASK_DEMO_3);
    }

    @After
    public void closeDb() throws InterruptedException{
        database.close();
    }

    @Test
    public void test_tasks_projectId_should_return_project_id_with_success() {
        assertEquals(FIRST_PROJECT_DEMO.getId(), TASK_DEMO_1.getProjectId());
        assertEquals(SECOND_PROJECT_DEMO.getId(), TASK_DEMO_2.getProjectId());
        assertEquals(THIRD_PROJECT_DEMO.getId(), TASK_DEMO_3.getProjectId());
    }

    @Test
    public void test_az_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(TASK_DEMO_1);
        tasks.add(TASK_DEMO_2);
        tasks.add(TASK_DEMO_3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), TASK_DEMO_3);
        assertSame(tasks.get(1), TASK_DEMO_2);
        assertSame(tasks.get(2), TASK_DEMO_1);
    }

    @Test
    public void test_za_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(TASK_DEMO_1);
        tasks.add(TASK_DEMO_2);
        tasks.add(TASK_DEMO_3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), TASK_DEMO_1);
        assertSame(tasks.get(1), TASK_DEMO_2);
        assertSame(tasks.get(2), TASK_DEMO_3);
    }

    @Test
    public void test_recent_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(TASK_DEMO_1);
        tasks.add(TASK_DEMO_2);
        tasks.add(TASK_DEMO_3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), TASK_DEMO_3);
        assertSame(tasks.get(1), TASK_DEMO_2);
        assertSame(tasks.get(2), TASK_DEMO_1);
    }

    @Test
    public void test_old_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(TASK_DEMO_1);
        tasks.add(TASK_DEMO_2);
        tasks.add(TASK_DEMO_3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), TASK_DEMO_1);
        assertSame(tasks.get(1), TASK_DEMO_2);
        assertSame(tasks.get(2), TASK_DEMO_3);
    }
}