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

/**
 DATA SET FOR TEST _______________________________________________________________________________
 */

        private static final Project[] PROJECTS = Project.getAllProjects();
        private static final long PROJECT_ID = PROJECTS[0].getId();
        private static final int SIZE_PROJECT = PROJECTS.length;

/**
  CREATE LIST PROJECT AND GET LIST PROJECT _________________________________________________________
*/

        @Test
        public void create_All_Projects_And_Get_All_Project() throws InterruptedException{
// BEFORE : ADDING A NEW TASK ----------------------------------------------------------------------
             this.database.projectDao().Create_All_Project(PROJECTS);
// GET ---------------------------------------------------------------------------------------------
             Project[] projects = LiveDataTestUtil.getValue(this.database.projectDao().get_All_Projects());
// TEST --------------------------------------------------------------------------------------------
             assertEquals(SIZE_PROJECT, projects.length);
         }


    }

