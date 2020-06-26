package com.athand.todoc.ui;

import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.athand.todoc.injections.Injection;
import com.athand.todoc.injections.ViewModelFactory;
import com.athand.todoc.R;
import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */

public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener , DialogAlert.Interface_AlertDialog {

    /**
     * List of all projects available in the application
     */
    private Project[] allProjects;

    /**
     * List of all current tasks of the application
     */
    @NonNull
    private  ArrayList<Task> tasks = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private final TasksAdapter adapter = new TasksAdapter(tasks, this);

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private static SortMethod sortMethod = SortMethod.NONE;

    /**
     * The RecyclerView which displays the list of tasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    /**
     * The ItemViewModel that allows the database access
     */

    private ItemViewModel itemViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configuration_View();

        ItemViewModel_manager();
        itemViewModel.init();

        get_Project();
        get_Tasks();
        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }

        get_Tasks();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        itemViewModel.delete_Task(task.getId());
    }

/**
 CONFIGURATION OF THE VIEW __________________________________________________________________
 */

    private void configuration_View(){
        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);
    }

/**
 CONFIGURATION OF DATABASE ACCESS __________________________________________________________________
 */

    private void ItemViewModel_manager(){
        ViewModelFactory viewModelFactory = Injection.provide_View_Model_Factory(this);
        this.itemViewModel= ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel.class);
    }

/**
 MANAGE THE LIST OF PROJECTS IN THE DATABASE _______________________________________________________
 */

// GET THE LIST OF PROS AND AUTOMATICALLY UPDATE EVERY CHANGE ______________________________________
    private void get_Project(){
        itemViewModel.get_All_Projects().observe(this,this::update_Project);
    }

// METHOD THAT UPDATES allProjects _________________________________________________________________
    private void update_Project(Project[] project){
        allProjects = project;
    }

/**
 MANAGE TASK LIST DISPLAY ________________________________________________________________________
 */

// GET LIST TASK AND AUTOMATICALLY UPDATE IT EVERY CHANGE __________________________________________
    private void get_Tasks() {
        this.itemViewModel.get_Tasks().observe(this,this::updateTasks);
    }

// UPDATES THE LIST OF TASKS IN THE UI _____________________________________________________________

    private void updateTasks(List<Task> tasks) {
        this.tasks= (ArrayList<Task>) tasks;
        if (tasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;

            }
            adapter.updateTasks(tasks);
        }
    }

/**
     * Shows the Dialog for adding a Task __________________________________________________________
     */

    private void showAddTaskDialog() {
        final DialogAlert dialog = new DialogAlert();
        dialog.set_Project(allProjects);
        dialog.show(getSupportFragmentManager(),null);
    }

/**
     * Adds the given task to the list of created tasks. ___________________________________________
     *
     * @param task the task to be added to the list
     */

    private void addTask(@NonNull Task task) {
        this.itemViewModel.inset_Task(task);
    }

/**
  GET DATA AND CREATE TASK. ________________________________________________________________________
 */

    @Override
    public void data_Callback_AlertDialog_To_Add_A_Task(String taskName, Project taskProject) {
        Task task = new Task(
                taskProject.getId(),
                taskName,
                new Date().getTime());
        addTask(task);
    }

/**
     * List of all possible sort methods for task __________________________________________________
     */

    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

}
