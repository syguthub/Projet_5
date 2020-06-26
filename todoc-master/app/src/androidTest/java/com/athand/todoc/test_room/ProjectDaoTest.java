package com.athand.todoc.test_room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.support.annotation.NonNull;
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

/**
 * DataBase test for project
 *
 * @author GASSAMA SOULEYMAN
 */

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
                    .addCallback(prepopulateDataBase())
                    .build();
            }

// SET PROJECT IN DATA PLACED IN THE DATABASE TO TEST__________________________________________________________
        private static RoomDatabase.Callback prepopulateDataBase() {
                return new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Project[] projects=Project.getAllProjects();

                        for (Project project : projects) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("id", project.getId());
                            contentValues.put("name", project.getName());
                            contentValues.put("color", project.getColor());
                            db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                        }
                    }
                };
        }

        @After
        public void closeDb() throws Exception{
            database.close();
        }

/**
 DATA SET FOR TEST _______________________________________________________________________________
 */

        private static final Project[] PROJECTS = Project.getAllProjects();
        private static final int SIZE_PROJECT = PROJECTS.length;

/**
  CREATE LIST PROJECT AND GET LIST PROJECT _________________________________________________________
*/

        @Test
        public void Get_All_Project() throws InterruptedException{
// GET ---------------------------------------------------------------------------------------------
             Project[] projects = LiveDataTestUtil.getValue(this.database.projectDao().get_All_Projects());
// TEST --------------------------------------------------------------------------------------------
             assertEquals(SIZE_PROJECT, projects.length);
         }

    }

