package com.athand.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.athand.todoc.dataBase.dao.ProjectDao;
import com.athand.todoc.model.Project;

/*
THIS CLASS ALLOWS FOR INSTANCING THE INTERFACE ProjectDao

 */

public class ProjectDaoRepository {

    private final ProjectDao projectDao;

    public ProjectDaoRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

/**
 GET _____________________________________________________________________________________________
 */

    public LiveData <Project[]> get_All_Projects(){
        return this.projectDao.get_All_Projects();
    }

}
