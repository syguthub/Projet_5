package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDaoRepository;
import com.cleanup.todoc.repository.TaskDaoRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    private final TaskDaoRepository taskDaoSource;
    private final ProjectDaoRepository projectDaoSource;
    private final Executor executor;


    private LiveData<List<Task>> current_Tasks;

    public ItemViewModel(TaskDaoRepository taskDaoSource, ProjectDaoRepository projectDaoSource, Executor executor) {
        this.taskDaoSource = taskDaoSource;
        this.projectDaoSource = projectDaoSource;
        this.executor = executor;
    }

    public void init(List<Task> tasks){
        if (this.current_Tasks != null){
            return;
        }
        current_Tasks = this.taskDaoSource.getTasks();
    }


    public void Create_All_Project(Project[] Project){
        executor.execute( ()-> this.projectDaoSource.Create_All_Project(Project) );
    }

    public void Create_Project(Project project){
        executor.execute( ()-> this.projectDaoSource.Create_Project(project) );
    }

    public void inset_Task (Task task){
        executor.execute( ()-> this.taskDaoSource.inset_Task(task) );
    }

    public LiveData<List<Task>> getTasks(){
        return this.taskDaoSource.getTasks();
    }

    public LiveData<Task> getTask(long Id){
        return this.taskDaoSource.getTask(Id);
    }

    public void update_Task(Task task){
        executor.execute( ()-> this.taskDaoSource.update_Task(task) );
    }

    public void update_Tasks(List<Task> tasks){
        executor.execute( ()-> this.taskDaoSource.update_Tasks(tasks) );
    }

    public void delete_Task(long taskId){
        executor.execute( ()-> this.taskDaoSource.delete_Task(taskId) );
    }



}

