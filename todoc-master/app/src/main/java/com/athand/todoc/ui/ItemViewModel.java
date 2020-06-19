package com.athand.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;
import com.athand.todoc.repository.ProjectDaoRepository;
import com.athand.todoc.repository.TaskDaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/*
THIS CLASS ALLOWS TO SAVE GRAPHIC INTERFACE DATA WHEN CHANGING THE CONFIGURATION.
WE RECOVER THEM HERE AND REAPSHEETS IN OUR CASE THE LIST Task DISPLAY ORDER

*/

public class ItemViewModel extends ViewModel {

    private final TaskDaoRepository taskDaoSource;
    private final ProjectDaoRepository projectDaoSource;
    private final Executor executor;

/**
 CONSTRUCTOR _______________________________________________________________________________________
 */

    public ItemViewModel(TaskDaoRepository taskDaoSource, ProjectDaoRepository projectDaoSource, Executor executor) {
        this.taskDaoSource = taskDaoSource;
        this.projectDaoSource = projectDaoSource;
        this.executor = executor;
    }


/**
 CREATE PROJECT ____________________________________________________________________________________
 */

    public void Create_All_Project(Project[] Project){
        executor.execute( ()-> this.projectDaoSource.Create_All_Project(Project) );
    }

/**
 GET PROJECT _______________________________________________________________________________________
 */

    public LiveData<Project[]> get_All_Projects(){
        return this.projectDaoSource.get_All_Projects();
    }

/**
 CREATE TASK _______________________________________________________________________________________
 */

    public void inset_Task (Task task){
        executor.execute( ()-> this.taskDaoSource.inset_Task(task) );
    }

/**
 GET LIST TASK _____________________________________________________________________________________
 */

    public LiveData<List<Task>> get_Tasks_Oder_Alphabetical() {
        return this.taskDaoSource.get_Tasks_Oder_Alphabetical();
    }

    public LiveData<List<Task>> get_Tasks_Oder_Alphabetical_Inverse() {
        return this.taskDaoSource.get_Tasks_Oder_Alphabetical_Inverse();
    }

    public LiveData<List<Task>> get_Tasks_Oder_Recent_First() {
        return this.taskDaoSource.get_Tasks_Oder_Recent_First();
    }

    public LiveData<List<Task>> get_Tasks_Oder_Old_First(){
        return this.taskDaoSource.get_Tasks_Oder_Old_First();
    }

/**
 DELETE TASK _______________________________________________________________________________________
 */

    public void delete_Task(long taskId){
        executor.execute( ()-> this.taskDaoSource.delete_Task(taskId) );
    }

}

