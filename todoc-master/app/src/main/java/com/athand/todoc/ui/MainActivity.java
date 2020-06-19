package com.athand.todoc.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.athand.todoc.Injections.Injection;
import com.athand.todoc.Injections.ViewModelFactory;
import com.athand.todoc.R;
import com.athand.todoc.model.Project;
import com.athand.todoc.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

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
    public static SortMethod sortMethod = SortMethod.NONE;

    /**
     * Dialog to create a new task
     */
    @Nullable
    private AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

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
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    private ItemViewModel itemViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);

        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());

        ItemViewModel_manager();

        get_Project();
        get_Tasks();
    }

    @Override
    protected void onResume() {
        get_Tasks();
        super.onResume();
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
        visibility_Tasks();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        itemViewModel.delete_Task(task.getId());
    }

/**
 CONFIGURATION OF DATABASE ACCESS __________________________________________________________________
 */

    private void ItemViewModel_manager(){
        ViewModelFactory viewModelFactory = Injection.provide_View_Model_Factory(this);
        this.itemViewModel= ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel.class);
// CREATION OF BASIC PROJECTS ----------------------------------------------------------------------
        this.itemViewModel.Create_All_Project(Project.getAllProjects());
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
//SELECT THE TASK DISPLAY ORDER --------------------------------------------------------------------
        switch (sortMethod) {
            case ALPHABETICAL:
                this.itemViewModel.get_Tasks_Oder_Alphabetical().observe(this,this::update_Tasks);
                break;
            case ALPHABETICAL_INVERTED:
                this.itemViewModel.get_Tasks_Oder_Alphabetical_Inverse().observe(this,this::update_Tasks);
                break;
            case RECENT_FIRST:
                this.itemViewModel.get_Tasks_Oder_Recent_First().observe(this,this::update_Tasks);
                break;
            case OLD_FIRST:
            case NONE:
                this.itemViewModel.get_Tasks_Oder_Old_First().observe(this,this::update_Tasks);
                break;
        }
    }

// METHOD THAT UPDATES THE DISPLAY _________________________________________________________________
    private void update_Tasks(List<Task> tasks){
// ICON VISIBILITY UPDATE --------------------------------------------------------------------------
        visibility_Tasks();
// TASK UPDATE -------------------------------------------------------------------------------------
        this.tasks= (ArrayList<Task>) tasks;
// RECYCLERVIEW UPDATE -----------------------------------------------------------------------------
        adapter.updateTasks(this.tasks);
        listTasks.setAdapter(adapter);
    }

/**
     * Called when the user clicks on the positive button of the Create Task Dialog. _______________
     *
     * @param dialogInterface the current displayed dialog
     */

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                Task task = new Task(
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );

                addTask(task);
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

/**
     * Shows the Dialog for adding a Task __________________________________________________________
     */

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

/**
     * Adds the given task to the list of created tasks. ___________________________________________
     *
     * @param task the task to be added to the list
     */

    private void addTask(@NonNull Task task) {
        itemViewModel.inset_Task(task);
    }

/**
     * ICON VISIBILITY MANAGEMENT __________________________________________________________________
     */

    private void visibility_Tasks() {
            if (this.tasks.size() == 0) {
                lblNoTasks.setVisibility(View.VISIBLE);
                listTasks.setVisibility(View.GONE);
            } else {
                lblNoTasks.setVisibility(View.GONE);
                listTasks.setVisibility(View.VISIBLE);
            }
        }

/**
     * Returns the dialog allowing the user to create a new task. __________________________________
     *
     * @return the dialog allowing the user to create a new task
     */

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);
// ALERTDIALOG ELEMENT CONFIGURATION ---------------------------------------------------------------
        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

// This instead of listener to positive button in order to avoid automatic dismiss -----------------
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

/**
     * Sets the data of the Spinner with projects to associate to a new task _______________________
     */

    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
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
