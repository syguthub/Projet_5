package com.athand.todoc.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.athand.todoc.model.Project;

/**
 DAO INTERFACE FOR MANAGING PROJECT-TYPE DATA ACCESS IN THE DATABASE
 IT PERFORMS SQL REQUEST
*/

@Dao
public interface ProjectDao {

/**
 GET _____________________________________________________________________________________________
 */

    @Query("SELECT*FROM Project" )
    LiveData <Project[]> get_All_Projects();

}
