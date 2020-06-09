package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.dataBase.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDaoRepository {

    private final TaskDao taskDao;

    public TaskDaoRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    public LiveData<List<Task>> getTasks(){
        return this.taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasksOderAlphabetical() {
        return this.taskDao.getTasksOderAlphabetical();
    }

    public LiveData<List<Task>> getTasksOderAlphabeticalInverse() {
        return this.taskDao.getTasksOderAlphabeticalInverse();
    }

    public LiveData<List<Task>> getTasksOderRecentFirst() {
        return this.taskDao.getTasksOderRecentFirst();
    }

    public LiveData<List<Task>> getTasksRecentFirstInverse(){
        return this.taskDao.getTasksRecentFirstInverse();
    }



    public LiveData<Task> getTask(long Id){
        return this.taskDao.getTask(Id);
    }

    public void inset_Task (Task task){
        this.taskDao.inset_Task(task);
    }

    public void update_Task(Task task){
        this.taskDao.update_Task(task);
    }

    public void update_Tasks(List<Task> tasks){
        this.taskDao.update_Tasks(tasks);
    }

    public void delete_Task(long taskId){
        this.taskDao.delete_Task(taskId);
    }

}

