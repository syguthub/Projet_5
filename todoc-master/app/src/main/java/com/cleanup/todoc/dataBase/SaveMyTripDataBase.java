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
                contentValues.put("username","Phylippe");
                contentValues.put("urlPicture","https://oc-user.imgix.net/" +
                        "users/avatars/15175844164713_frame_523.jpg?auto=compress,format&q=80&h=100&dpr=2");
                db.insert("User", OnConflictStrategy.IGNORE,contentValues);
            }
        };
    }

}
