package com.athand.todoc.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.athand.todoc.model.Task;

import java.util.List;

/**
DAO INTERFACE FOR THE MANAGEMENT OF ACCESS TO DATA OF THE TASK TYPE IN THE DATABASE.
IT PERFORMS SQL REQUEST
*/

@Dao
public interface TaskDao {

/**
 CREATE ____________________________________________________________________________________________
 */

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void inset_Task (Task task);

/**
 GET _______________________________________________________________________________________________
 */

    @Query( "SELECT*FROM Task" )
    LiveData<List<Task>> get_Tasks();

/**
 DELETE ____________________________________________________________________________________________
 */

    @Query("DELETE FROM Task WHERE id = :taskId")
    void delete_Task(long taskId);

}
