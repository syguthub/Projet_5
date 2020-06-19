package com.athand.todoc.dataBase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.athand.todoc.model.Project;

import java.util.List;

/*
INTERFACE DAO POUR LA GESTION DES ACCES AU DONEE DE TYPE Project DANS LA BASE DE DONNEE
IL EFFECTUE LES REQUET SQL
*/
@Dao
public interface ProjectDao {

/**
 CREATE __________________________________________________________________________________________
 */

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void Create_All_Project(Project[] Project);

/**
 GET _____________________________________________________________________________________________
 */

    @Query("SELECT*FROM Project" )
    LiveData <Project[]> get_All_Projects();

}
