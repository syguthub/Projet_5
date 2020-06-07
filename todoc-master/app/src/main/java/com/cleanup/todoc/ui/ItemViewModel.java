package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDaoRepository;
import com.cleanup.todoc.repository.TaskDaoRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    private final TaskDaoRepository taskDaoSource;
    private final ProjectDaoRepository projectDaoSource;
    private final Executor executor;


    private LiveData<Task> current_Task;

    public ItemViewModel(TaskDaoRepository taskDaoSource, ProjectDaoRepository projectDaoSource, Executor executor) {
        this.taskDaoSource = taskDaoSource;
        this.projectDaoSource = projectDaoSource;
        this.executor = executor;
    }

    public void init(long taskId){
        if (this.current_Task != null){
            return;
        }
        current_Task = this.taskDaoSource.getTask(taskId);
    }

    public LiveData<List<Task>> getTasks(){
        return this.taskDaoSource.getTasks();
    }

    public LiveData<Task> getTask(long Id){
        return this.taskDaoSource.getTask(Id);
    }

    public void inset_Task (Task task){
        executor.execute( ()-> this.taskDaoSource.inset_Task(task) );
    }

    public void update_Task(Task task){
        executor.execute( ()-> this.taskDaoSource.update_Task(task) );
    }

    public void delete_Task(long taskId){
        executor.execute( ()-> this.taskDaoSource.delete_Task(taskId) );
    }



}

