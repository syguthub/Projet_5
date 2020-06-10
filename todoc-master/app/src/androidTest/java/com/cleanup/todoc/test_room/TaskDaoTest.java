package com.cleanup.todoc.test_room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.dataBase.SaveMyTripDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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
    private static Project[] PROJECT = Project.getAllProjects();
    private static final int TASK_ID1 = 1;
    private  Task TASK_DEMO1 = new Task(TASK_ID1, PROJECT[0].getId(), "test A", 1);

    private static final int TASK_ID2 = 2;
    private  Task TASK_DEMO2 = new Task(TASK_ID2, PROJECT[2].getId(), "test B", 2);

    private static final int TASK_ID3 = 3;
    private  Task TASK_DEMO3 = new Task(TASK_ID3, PROJECT[1].getId(), "test C", 3);

    private List<Task> LIST_TASK = Arrays.asList(TASK_DEMO1, TASK_DEMO2, TASK_DEMO3);
    private List<Task> LIST_TASK_ALPHABETICAL = Arrays.asList(TASK_DEMO1, TASK_DEMO2, TASK_DEMO3);
    private List<Task> LIST_TASK_ALPHABETICAL_INVERSE = Arrays.asList(TASK_DEMO3, TASK_DEMO2, TASK_DEMO1);
    private List<Task> LIST_TASK_RECENT_FIRST = Arrays.asList(TASK_DEMO3, TASK_DEMO1, TASK_DEMO2);
    private List<Task> LIST_TASK_OLD_FIRST = Arrays.asList(TASK_DEMO2, TASK_DEMO1, TASK_DEMO3);

// GET LIST TASK ___________________________________________________________________________________
    @Test
    public void insert_Task_And_Get_Tasks_List() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        List<Task> tasks = new ArrayList<>();
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks());
        assertEquals(0, tasks.size());

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO3);

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks());
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getName().equals(TASK_DEMO1.getName()));
    }

// GET TASKS LIST DIFFERENT ODER ___________________________________________________________________
    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Alphabetical() throws InterruptedException {
        // BEFORE : ADDING A NEW TASK
        List<Task> tasks = new ArrayList<>();
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
        List<Task> tasks = new ArrayList<>();
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
        List<Task> tasks = new ArrayList<>();
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
        List<Task> tasks = new ArrayList<>();
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
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getId() == (TASK_DEMO1.getId()));

        this.database.taskDao().delete_Task(TASK_ID1);

        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks());
        assertEquals(0, tasks.size());
    }
}

