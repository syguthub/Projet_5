package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.dataBase.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDaoRepository {

    private final TaskDao taskDao;

    public TaskDaoRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    public LiveData<List<Task>> get_Tasks(){
        return this.taskDao.get_Tasks();
    }

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


    public void inset_Task (Task task){
        this.taskDao.inset_Task(task);
    }


    public void delete_Task(long taskId){
        this.taskDao.delete_Task(taskId);
    }

}

