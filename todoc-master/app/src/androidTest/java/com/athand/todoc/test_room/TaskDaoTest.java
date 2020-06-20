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

/**
 SYNCHRONOUS TEST PROCEDURE ________________________________________________________________________
 */

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule;

/**
 CREATE A UNIQUE DATABASE FOR EACH TEXT ____________________________________________________________
 */

    private SaveMyTripDataBase database;

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

/**
 DATA SET FOR TEST _________________________________________________________________________________
 */

    private static final Project[] PROJECT = Project.getAllProjects();
    private final Task TASK_DEMO1 = new Task( PROJECT[0].getId(), "test A", 1);
    private final Task TASK_DEMO2 = new Task( PROJECT[2].getId(), "test B", 2);
    private final Task TASK_DEMO3 = new Task( PROJECT[1].getId(), "test C", 3);

    private List<Task> tasks = new ArrayList<>();

/**
 INSERT TASK AND GET LIST TASK _____________________________________________________________________
*/

    @Test
    public void insert_Task_And_Get_Tasks_List() throws InterruptedException {
// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(0, tasks.size());
// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO3);

// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getName().equals(TASK_DEMO1.getName()));
    }

/**
 GET TASKS LIST DIFFERENT ODER _____________________________________________________________________
 */

    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Alphabetical() throws InterruptedException {
// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(0, tasks.size());
// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO3);

// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO1.getName()) && tasks.get(0).getName().equals(TASK_DEMO1.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO2.getName()) && tasks.get(1).getName().equals(TASK_DEMO2.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO3.getName()) && tasks.get(2).getName().equals(TASK_DEMO3.getName()));
    }

    @Test
    public void insert_Task_And_get_Tasks_Oder_Alphabetical_Inverse() throws InterruptedException {
// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical_Inverse());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(0, tasks.size());
// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO3);

// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Alphabetical_Inverse());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO3.getName()) && tasks.get(0).getName().equals(TASK_DEMO3.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO2.getName()) && tasks.get(1).getName().equals(TASK_DEMO2.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO1.getName()) && tasks.get(2).getName().equals(TASK_DEMO1.getName()));
    }

    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Recent_First() throws InterruptedException {
// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Recent_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(0, tasks.size());

// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
        TASK_DEMO1.setCreationTimestamp(2);
        TASK_DEMO2.setCreationTimestamp(1);
        TASK_DEMO3.setCreationTimestamp(3);

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO3);

// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Recent_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO3.getName()) && tasks.get(0).getName().equals(TASK_DEMO3.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO1.getName()) && tasks.get(1).getName().equals(TASK_DEMO1.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO2.getName()) && tasks.get(2).getName().equals(TASK_DEMO2.getName()));
    }

    @Test
    public void insert_Task_And_Get_Tasks_List_Oder_Old_First() throws InterruptedException {
// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(0, tasks.size());

// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
        TASK_DEMO1.setCreationTimestamp(2);
        TASK_DEMO2.setCreationTimestamp(1);
        TASK_DEMO3.setCreationTimestamp(3);

        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO2);
        this.database.taskDao().inset_Task(TASK_DEMO1);
        this.database.taskDao().inset_Task(TASK_DEMO3);

// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0).getName().equals(TASK_DEMO2.getName()) && tasks.get(0).getName().equals(TASK_DEMO2.getName()));
        assertTrue(tasks.get(1).getName().equals(TASK_DEMO1.getName()) && tasks.get(1).getName().equals(TASK_DEMO1.getName()));
        assertTrue(tasks.get(2).getName().equals(TASK_DEMO3.getName()) && tasks.get(2).getName().equals(TASK_DEMO3.getName()));
    }

/**
 DELETE ____________________________________________________________________________________________
 */

    @Test
    public void insert_Task_And_Delete_Task() throws InterruptedException {
// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
        this.database.projectDao().Create_All_Project(PROJECT);
        this.database.taskDao().inset_Task(TASK_DEMO1);

// GET ---------------------------------------------------------------------------------------------
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(tasks.get(0).getName(), TASK_DEMO1.getName());
// DELETE ------------------------------------------------------------------------------------------
        this.database.taskDao().delete_Task(tasks.get(0).getId());
// GET ---------------------------------------------------------------------------------------------
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().get_Tasks_Oder_Old_First());
// TEST --------------------------------------------------------------------------------------------
        assertEquals(0, tasks.size());
    }

}

