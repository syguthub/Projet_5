package com.athand.todoc.Injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.athand.todoc.repository.ProjectDaoRepository;
import com.athand.todoc.repository.TaskDaoRepository;
import com.athand.todoc.ui.ItemViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskDaoRepository taskDaoSource;
    private final ProjectDaoRepository projectDaoSource;
    private final Executor executor;

    public ViewModelFactory(TaskDaoRepository taskDaoSource, ProjectDaoRepository projectDaoSource, Executor executor) {
        this.taskDaoSource = taskDaoSource;
        this.projectDaoSource = projectDaoSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(taskDaoSource,projectDaoSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
