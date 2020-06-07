package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDaoRepository;
import com.cleanup.todoc.repository.TaskDaoRepository;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    private final TaskDaoRepository taskDaoRepository;
    private final ProjectDaoRepository projectDaoRepository;
    private final Executor executor;


    private LiveData<Task> current_Task;

    public ItemViewModel(TaskDaoRepository taskDaoRepository, ProjectDaoRepository projectDaoRepository, Executor executor) {
        this.taskDaoRepository = taskDaoRepository;
        this.projectDaoRepository = projectDaoRepository;
        this.executor = executor;
    }

    public void init(long taskId){
        if (this.current_Task != null){
            return;
        }
        current_Task = this.taskDaoRepository.getTask(taskId);
    }

    public LiveData<List<Task>> getTasks(){
        return this.taskDaoRepository.getTasks();
    }

    public LiveData<Task> getTask(long Id){
        return this.taskDaoRepository.getTask(Id);
    }

    public void inset_Task (Task task){
        executor.execute( ()-> this.taskDaoRepository.inset_Task(task) );
    }

    public void update_Task(Task task){
        executor.execute( ()-> this.taskDaoRepository.update_Task(task) );
    }

    public void delete_Task(long taskId){
        executor.execute( ()-> this.taskDaoRepository.delete_Task(taskId) );
    }



}

