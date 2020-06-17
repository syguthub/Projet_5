package com.athand.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;
import com.athand.todoc.repository.ProjectDaoRepository;
import com.athand.todoc.repository.TaskDaoRepository;

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

//    public void init(){
//        if (this.current_Tasks != null){
//            return;
//        }
//        switch (sortMethod) {
//            case ALPHABETICAL:
//                current_Tasks = this.taskDaoSource.get_Tasks_Oder_Alphabetical();
//                break;
//            case ALPHABETICAL_INVERTED:
//                current_Tasks = this.taskDaoSource.get_Tasks_Oder_Alphabetical_Inverse();
//                break;
//            case RECENT_FIRST:
//                current_Tasks = this.taskDaoSource.get_Tasks_Oder_Recent_First();
//                break;
//            case OLD_FIRST:
//                current_Tasks = this.taskDaoSource.get_Tasks_Oder_Old_First();
//                break;
//            case NONE:
//                current_Tasks = this.taskDaoSource.get_Tasks();
//                break;
//        }
//    }


    public void Create_All_Project(Project[] Project){
        executor.execute( ()-> this.projectDaoSource.Create_All_Project(Project) );
    }

    public LiveData<Project[]> get_All_Projects(){
        return this.projectDaoSource.get_All_Projects();
    }


    public void inset_Task (Task task){
        executor.execute( ()-> this.taskDaoSource.inset_Task(task) );
    }

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

    public void delete_Task(long taskId){
        executor.execute( ()-> this.taskDaoSource.delete_Task(taskId) );
    }



}

