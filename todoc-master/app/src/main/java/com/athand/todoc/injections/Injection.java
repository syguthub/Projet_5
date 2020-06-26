package com.athand.todoc.injections;

import android.content.Context;

import com.athand.todoc.dataBase.SaveMyTripDataBase;
import com.athand.todoc.repository.ProjectDaoRepository;
import com.athand.todoc.repository.TaskDaoRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
CLASS FOR INSTANCING OBJECTS
*/

public class Injection {

/**
 STATIC METHOD FOR INSTANCING ALL TYPE TaskDaoRepository OBJECTS ___________________________________
 */

    private static TaskDaoRepository provide_Task_Dao_Source(Context context){
        SaveMyTripDataBase dataBase = SaveMyTripDataBase.getINSTANCE(context);
        return new TaskDaoRepository( dataBase.taskDao() );
    }

/**
 STATIC METHOD FOR INSTANCING ALL TYPE ProjectDaoRepository OBJECTS ________________________________
 */

    private static ProjectDaoRepository provide_Project_Dao_Source(Context context){
        SaveMyTripDataBase dataBase = SaveMyTripDataBase.getINSTANCE(context);
        return new ProjectDaoRepository( dataBase.projectDao() );
    }

/**
 STATIC METHOD FOR INSTANCING ALL TYPE Executor OBJECTS ____________________________________________
 */

    private static Executor provide_Executor(){
        return Executors.newSingleThreadExecutor();
    }

/**
 STATIC METHOD FOR INSTANCING ALL TYPE ViewModelFactory OBJECTS ____________________________________
 */

    public static ViewModelFactory provide_View_Model_Factory(Context context){

        TaskDaoRepository taskDaoSource = provide_Task_Dao_Source(context);
        ProjectDaoRepository projectDaoSource = provide_Project_Dao_Source(context);
        Executor executor= provide_Executor();

        return new ViewModelFactory(taskDaoSource,projectDaoSource,executor);
    }

}
