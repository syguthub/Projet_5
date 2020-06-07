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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
    public class TaskDaoTest {
        private SaveMyTripDataBase database;

        @Rule
        public InstantTaskExecutorRule instantTaskExecutorRule;

        @Before
        public void initDb() throws Exception{
            this.database = Room.inMemoryDatabaseBuilder
                    (InstrumentationRegistry.getContext(),SaveMyTripDataBase.class)
                    .allowMainThreadQueries()
                    .build();
        }

        @After
        public void closeDb() throws Exception{
            database.close();
        }

        // DATA SET FOR TEST
        private static Project[] PROJECT = Project.getAllProjects();

        private static final int TASK_ID1 = 1;
        private static Task TASK_DEMO1 = new Task(TASK_ID1,PROJECT[0].getId(),"test1", new Date().getTime());
        private static Task TASK_DEMO1_UPDATE = new Task(TASK_ID1,PROJECT[0].getId(),"test1 UPDATE", new Date().getTime());

        private static final int TASK_ID2 = 2;
        private static Task TASK_DEMO3 = new Task(TASK_ID2,PROJECT[2].getId(),"test2", new Date().getTime());

        private static final int TASK_ID3 = 3;
        private static Task TASK_DEMO4 = new Task(TASK_ID3,PROJECT[1].getId(),"test3", new Date().getTime());

        @Test
        public void insert_Task_List_And_Get_Task_List() throws InterruptedException{
            // BEFORE : ADDING A NEW TASK
            this.database.projectDao().Create_All_Project(PROJECT);
            this.database.taskDao().inset_Task(TASK_DEMO1);
            this.database.taskDao().inset_Task(TASK_DEMO3);
            this.database.taskDao().inset_Task(TASK_DEMO4);

            // TEST
            List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
            assertEquals(3, tasks.size());
        }

        @Test
        public void insert_And_Get_Task() throws InterruptedException{
            // BEFORE : ADDING A NEW TASK
            this.database.projectDao().Create_Project(PROJECT[0]);
            this.database.taskDao().inset_Task(TASK_DEMO1);

            // TEST
            Task task = this.database.taskDao().getTask(TASK_ID1);
            assertTrue(task.getName().equals(TASK_DEMO1.getName()) && task.getName().equals(TASK_DEMO1.getName()));
        }

        @Test
        public void insert_Task_And_Update_Task() throws InterruptedException{
            // BEFORE : ADDING A NEW TASK
            this.database.projectDao().Create_Project(PROJECT[0]);
            this.database.taskDao().inset_Task(TASK_DEMO1);

            // TEST
            Task task = this.database.taskDao().getTask(TASK_ID1);
            assertTrue(task.getName().equals(TASK_DEMO1.getName()) && task.getId()==(TASK_DEMO1.getId()));

            this.database.taskDao().update_Task(TASK_DEMO1_UPDATE);
            task = this.database.taskDao().getTask(TASK_ID1);
            assertTrue(!task.getName().equals(TASK_DEMO1.getName()) && task.getId() == TASK_DEMO1.getId());
        }

        @Test
        public void insert_Task_And_Delete_Task() throws InterruptedException{
            // BEFORE : ADDING A NEW TASK
            this.database.projectDao().Create_Project(PROJECT[0]);
            this.database.taskDao().inset_Task(TASK_DEMO1);

            // TEST
            Task task = this.database.taskDao().getTask(TASK_ID1);
            assertTrue(task.getName().equals(TASK_DEMO1.getName()) && task.getId()==(TASK_DEMO1.getId()));

            this.database.taskDao().delete_Task(TASK_ID1);

            task = this.database.taskDao().getTask(TASK_ID1);
            assertNull(task);
        }
    }

