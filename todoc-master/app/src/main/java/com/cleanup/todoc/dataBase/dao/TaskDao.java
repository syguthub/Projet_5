package com.cleanup.todoc.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query( "SELECT*FROM Task" )
    LiveData<List<Task>> get_Tasks();

    @Query( "SELECT*FROM Task ORDER BY name " )
    LiveData<List<Task>> get_Tasks_Oder_Alphabetical();

    @Query( "SELECT*FROM Task ORDER BY name DESC" )
    LiveData<List<Task>> get_Tasks_Oder_Alphabetical_Inverse();

    @Query( "SELECT*FROM Task ORDER BY creationTimestamp" )
    LiveData<List<Task>> get_Tasks_Oder_Recent_First();

    @Query( "SELECT*FROM Task ORDER BY creationTimestamp DESC" )
    LiveData<List<Task>> get_Tasks_Oder_Old_First();


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void inset_Task (Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    void delete_Task(long taskId);

}
