package com.athand.todoc.test_room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.athand.todoc.dataBase.SaveMyTripDataBase;
import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
    public class TaskDaoTest {
    private SaveMyTripDataBase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule;

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder
                (InstrumentationRegistry.getContext(), SaveMyTripDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    // DATA SET FOR TEST
    private static final Project[] PROJECT = Project.getAllProjects();
    private static final int TASK_ID1 = 1;
    private final Task TASK_DEMO1 = new Task(TASK_ID1, PROJECT[0].getId(), "test A", 1);

    private static final int TASK_ID2 = 2;
    private final Task TASK_DEMO2 = new Task(TASK_ID2, PROJECT[2].getId(), "test B", 2);

    private static final int TASK_ID3 = 3;
    private final Task TASK_DEMO3 = new Task(TASK_ID3, PROJECT[1].getId(), "test C", 3);

    List<Task> tasks = new ArrayList<>();

// GET LIST TASK ___________________________________________________________________________________
    @Test
    public void insert_Task_And_Get_Tasks_List() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
        assertEquals(0, tasks.size());

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO3);

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getName().equals(TASK_DEMO1.getName()));
    }

// GET TASKS LIST DIFFERENT ODER ___________________________________________________________________
    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Alphabetical() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical());
        assertEquals(0, tasks.size());

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO3);

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical());
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getName().equals(TASK_DEMO1.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO2.getName()) && tasks.get(1).getName().equals(TASK_DEMO2.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO3.getName()) && tasks.get(2).getName().equals(TASK_DEMO3.getName()));
    }

    @Test
    public void insert_Task_And_get_Tasks_Oder_Alphabetical_Inverse() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical_Inverse());
        assertEquals(0, tasks.size());

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO3);

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical_Inverse());
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO3.getName()) && tasks.get(0).getName().equals(TASK_DEMO3.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO2.getName()) && tasks.get(1).getName().equals(TASK_DEMO2.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO1.getName()) && tasks.get(2).getName().equals(TASK_DEMO1.getName()));
    }

    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Recent_First() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Recent_First());
        assertEquals(0, tasks.size());

        TASK_DEMO1.setCreationTimestamp(2);
        TASK_DEMO2.setCreationTimestamp(1);
        TASK_DEMO3.setCreationTimestamp(3);

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO3);

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Recent_First());
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO3.getName()) && tasks.get(0).getName().equals(TASK_DEMO3.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO1.getName()) && tasks.get(1).getName().equals(TASK_DEMO1.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO2.getName()) && tasks.get(2).getName().equals(TASK_DEMO2.getName()));
    }

    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Old_First() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
        assertEquals(0, tasks.size());

        TASK_DEMO1.setCreationTimestamp(2);
        TASK_DEMO2.setCreationTimestamp(1);
        TASK_DEMO3.setCreationTimestamp(3);

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO3);

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO2.getName()) && tasks.get(0).getName().equals(TASK_DEMO2.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO1.getName()) && tasks.get(1).getName().equals(TASK_DEMO1.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO3.getName()) && tasks.get(2).getName().equals(TASK_DEMO3.getName()));
    }

// DELETE __________________________________________________________________________________________
    @Test
    public void insert_Task_And_Delete_Task() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        this.database.projectDao().Create_Project(PROJECT[0]);
        this.database.taskDao().inset_Task(TASK_DEMO1);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getId() == (TASK_DEMO1.getId()));

        this.database.taskDao().delete_Task(TASK_ID1);

        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
        assertEquals(0, tasks.size());
    }
}

