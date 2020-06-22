package com.athand.todoc.dataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.athand.todoc.dataBase.dao.ProjectDao;
import com.athand.todoc.dataBase.dao.TaskDao;
import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;

import java.util.Date;

/**
CENTRALIZE THE DAO INTERFACE THAT MANAGES ACCESS TO DATABASES OF THE DATABASE

CREATE A SINGLE INSTANCE "INSTANCE" IN ORDER TO BE ABLE TO ACCESS THE SAME DATA IN ALL THE CLASSES
FOR THIS WHEN USING THIS CLASS "SaveMyTripDataBase", WE INSTANCE WITH ITS STATIC getINSTANCE () METHOD.
*/

@Database(entities = {Task.class, Project.class},version = 1,exportSchema = false)
public abstract class SaveMyTripDataBase extends RoomDatabase {

/**
SINGLETON ________________________________________________________________________________________
 */

    private static volatile SaveMyTripDataBase INSTANCE;

/**
DAO ______________________________________________________________________________________________
 */

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static SaveMyTripDataBase getINSTANCE(Context context){
        if(INSTANCE==null){
            synchronized (SaveMyTripDataBase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    SaveMyTripDataBase.class,"MyDatabase.db")
                            .addCallback(prepopulateDataBase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

/**
 1st DATA PLACED IN THE DATABASE TO TEST__________________________________________________________
 */

    private static Callback prepopulateDataBase() {
        return new Callback() {
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

}
