package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.dataBase.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDaoRepository {

    private final ProjectDao projectDao;

    public ProjectDaoRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public void Create_Project(Project project){
        this.projectDao.Create_Project(project);
    }

    public void Create_All_Project(Project[] Project){
        this.projectDao.Create_All_Project(Project);
    }

    public LiveData <Project> get_Project(long id){
        return this.projectDao.get_Project(id);
    }

    public LiveData <List<Project>> get_All_Projects(){
        return this.projectDao.get_All_Projects();
    }

}
