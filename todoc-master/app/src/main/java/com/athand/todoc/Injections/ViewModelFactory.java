package com.athand.todoc.Injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.athand.todoc.repository.ProjectDaoRepository;
import com.athand.todoc.repository.TaskDaoRepository;
import com.athand.todoc.ui.ItemViewModel;

import java.util.concurrent.Executor;

/**
THIS CLASS AND A CONSTRUCTOR.

THANKS TO THE IMPLEMENTATION "ViewModelProvider.Factory",
IT SIMPLIFIES THE CREATION OF LOBJET ItemViewModel
*/

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskDaoRepository taskDaoSource;
    private final ProjectDaoRepository projectDaoSource;
    private final Executor executor;

/**
 CONSTRUCTOR _______________________________________________________________________________________
 */

    public ViewModelFactory(TaskDaoRepository taskDaoSource, ProjectDaoRepository projectDaoSource, Executor executor) {
        this.taskDaoSource = taskDaoSource;
        this.projectDaoSource = projectDaoSource;
        this.executor = executor;
    }

/**
 RETURN CONSTRUCTED OBJECTS ________________________________________________________________________
 */

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(taskDaoSource,projectDaoSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
