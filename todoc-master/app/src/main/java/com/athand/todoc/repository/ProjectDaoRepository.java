package com.athand.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.athand.todoc.dataBase.dao.ProjectDao;
import com.athand.todoc.model.Project;

import java.util.List;

/*
THIS CLASS ALLOWS FOR INSTANCING THE INTERFACE ProjectDao

 */

public class ProjectDaoRepository {

    private final ProjectDao projectDao;

    public ProjectDaoRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

/**
 CREATE __________________________________________________________________________________________
 */

    public void Create_All_Project(Project[] Project){
        this.projectDao.Create_All_Project(Project);
    }

/**
 GET _____________________________________________________________________________________________
 */

    public LiveData <Project[]> get_All_Projects(){
        return this.projectDao.get_All_Projects();
    }

}