package com.cleanup.todoc.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void Create_Project(Project project);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void Create_All_Project(Project[] Project);

    @Query("SELECT*FROM Project WHERE id =:id" )
    LiveData <Project> get_Project(long id);

    @Query("SELECT*FROM Project" )
    LiveData <List<Project>> get_All_Projects();

}
