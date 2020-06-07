package com.cleanup.todoc.Injections;

import android.content.Context;

import com.cleanup.todoc.dataBase.SaveMyTripDataBase;
import com.cleanup.todoc.repository.ProjectDaoRepository;
import com.cleanup.todoc.repository.TaskDaoRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDaoRepository provide_Task_Dao_Source(Context context){
        SaveMyTripDataBase dataBase = SaveMyTripDataBase.getINSTANCE(context);
        return new TaskDaoRepository( dataBase.taskDao() );
    }

    public static ProjectDaoRepository provide_Project_Dao_Source(Context context){
        SaveMyTripDataBase dataBase = SaveMyTripDataBase.getINSTANCE(context);
        return new ProjectDaoRepository( dataBase.projectDao() );
    }

    public static Executor provide_Executor(Context context){
        SaveMyTripDataBase dataBase = SaveMyTripDataBase.getINSTANCE(context);
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provide_View_Model_Factory(Context context){

        TaskDaoRepository taskDaoSource = provide_Task_Dao_Source(context);
        ProjectDaoRepository projectDaoSource = provide_Project_Dao_Source(context);
        Executor executor= provide_Executor(context);

        return new ViewModelFactory(taskDaoSource,projectDaoSource,executor);
    }


}
