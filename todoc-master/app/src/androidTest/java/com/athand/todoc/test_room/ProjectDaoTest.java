package com.athand.todoc.test_room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.athand.todoc.dataBase.SaveMyTripDataBase;
import com.athand.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
    public class ProjectDaoTest {
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
        private static final long PROJECT_ID = 1L;
        private static final Project PROJECT = Project.getProjectById(PROJECT_ID);
        private static final Project[] PROJECTS = Project.getAllProjects();
        private static final int SIZE_PROJECT = PROJECTS.length;

        @Test
        public void create_Project_And_Get_Project() throws InterruptedException{
            // BEFORE : ADDING A NEW TASK
            this.database.projectDao().Create_Project(PROJECT);

            // TEST
            Project project= LiveDataTestUtil.getValue(this.database.projectDao().get_Project(PROJECT_ID));
            assertEquals(project.getId(), PROJECT.getId());
        }

        @Test
        public void create_All_Projects_And_Get_All_Project() throws InterruptedException{
            // BEFORE : ADDING A NEW TASK
            this.database.projectDao().Create_All_Project(PROJECTS);
            // TEST
            Project[] projects = LiveDataTestUtil.getValue(this.database.projectDao().get_All_Projects());
            assertEquals(SIZE_PROJECT, projects.length);
        }

    }

