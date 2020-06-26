package com.athand.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;
import com.athand.todoc.repository.ProjectDaoRepository;
import com.athand.todoc.repository.TaskDaoRepository;

import java.util.List;
import java.util.concurrent.Executor;

/*
THIS CLASS ALLOWS TO SAVE GRAPHIC INTERFACE DATA WHEN CHANGING THE CONFIGURATION.
WE RECOVER THEM HERE AND REAP SHEETS IN OUR CASE THE LIST Task DISPLAY ORDER

*/

public class ItemViewModel extends ViewModel {

    private final TaskDaoRepository taskDaoSource;
    private final ProjectDaoRepository projectDaoSource;
    private final Executor executor;

    private final LiveData <Project[]> projects;
    private LiveData <List<Task>> current_task;

/**
 CONSTRUCTOR _______________________________________________________________________________________
 */

    public ItemViewModel(TaskDaoRepository taskDaoSource, ProjectDaoRepository projectDaoSource, Executor executor) {
        this.taskDaoSource = taskDaoSource;
        this.projectDaoSource = projectDaoSource;
        this.executor = executor;
        projects=this.projectDaoSource.get_All_Projects();
    }

/**
 CREATE UNIQUE CURRENT TASK ________________________________________________________________________
 */

    void init (){
        if(current_task == null) {
            current_task = this.taskDaoSource.get_Tasks();
        }
    }

/**
 GET PROJECT _______________________________________________________________________________________
 */

    public LiveData<Project[]> get_All_Projects(){
        return projects;
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

    public LiveData<List<Task>> get_Tasks() {
        return current_task;
    }


/**
 DELETE TASK _______________________________________________________________________________________
 */

    public void delete_Task(long taskId){
        executor.execute( ()-> this.taskDaoSource.delete_Task(taskId) );
    }

}

