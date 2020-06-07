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
    LiveData<List<Task>> getTasks();

    @Query("SELECT*FROM Task WHERE id = :Id")
    LiveData<Task> getTask(long Id);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void inset_Task (Task task);

    @Update
    void update_Task(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    void delete_Task(long taskId);

}
