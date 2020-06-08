package com.cleanup.todoc.dataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.dataBase.dao.ProjectDao;
import com.cleanup.todoc.dataBase.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Date;

@Database(entities = {Task.class, Project.class},version = 1,exportSchema = false)
public abstract class SaveMyTripDataBase extends RoomDatabase {

//SINGLETON ________________________________________________________________________________________
    private static volatile SaveMyTripDataBase INSTANCE;

//DAO ______________________________________________________________________________________________
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

    private static Callback prepopulateDataBase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id",1);
                contentValues.put("ProjectId",3L);
                contentValues.put("name","test");
                contentValues.put("creationTimestamp",new Date().getTime());
                db.insert("Task", OnConflictStrategy.IGNORE,contentValues);
            }
        };
    }

}
