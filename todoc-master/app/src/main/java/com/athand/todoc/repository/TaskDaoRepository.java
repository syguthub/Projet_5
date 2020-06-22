package com.athand.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.athand.todoc.dataBase.dao.TaskDao;
import com.athand.todoc.model.Task;

import java.util.List;

/**
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

    public LiveData<List<Task>> get_Tasks() {
        return this.taskDao.get_Tasks();
    }

/**
 DELETE _____________________________________________________________________________________________
 */

    public void delete_Task(long taskId){
        this.taskDao.delete_Task(taskId);
    }

}

