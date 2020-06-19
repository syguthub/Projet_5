package com.athand.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.athand.todoc.dataBase.dao.TaskDao;
import com.athand.todoc.model.Task;

import java.util.List;

/*
THIS CLASS ALLOWS FOR INSTANCING THE INTERFACE TaskDao

 */

public class TaskDaoRepository {

    private final TaskDao taskDao;

    public TaskDaoRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

/**
 CREATE __________________________________________________________________________________________
 */

    public void inset_Task (Task task){
        this.taskDao.inset_Task(task);
    }

/**
 GET _____________________________________________________________________________________________
 */

    public LiveData<List<Task>> get_Tasks_Oder_Alphabetical() {
        return this.taskDao.get_Tasks_Oder_Alphabetical();
    }

    public LiveData<List<Task>> get_Tasks_Oder_Alphabetical_Inverse() {
        return this.taskDao.get_Tasks_Oder_Alphabetical_Inverse();
    }

    public LiveData<List<Task>> get_Tasks_Oder_Recent_First() {
        return this.taskDao.get_Tasks_Oder_Recent_First();
    }

    public LiveData<List<Task>> get_Tasks_Oder_Old_First(){
        return this.taskDao.get_Tasks_Oder_Old_First();
    }

/**
 DELETE _____________________________________________________________________________________________
 */

    public void delete_Task(long taskId){
        this.taskDao.delete_Task(taskId);
    }

}

