package com.athand.todoc.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.athand.todoc.R;
import com.athand.todoc.model.Project;

public class DialogAlert extends DialogFragment {

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
     * Inteeface_AlertDialog that allows to send the task data
     */

    Interface_AlertDialog interface_alertDialog = new MainActivity();

    /**
     * Project that allows to the spinner configure
     */

    private Project[] allProjects;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            interface_alertDialog= (Interface_AlertDialog) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "onAttach in DonneAlertDialig");
        }
    }

/**
 CREATE ALERT DIALOGUE _____________________________________________________________________________
 */

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

// CREATE VIEW WITCH INFLATER AND SET IN BUILDER ---------------------------------------------------
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity(), R.style.Dialog);
            alertBuilder.setTitle(R.string.add_task);
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View viewSpinner = inflater.inflate(R.layout.dialog_add_task, null);
            alertBuilder.setView(viewSpinner);
            alertBuilder.setPositiveButton(R.string.add, null);

// VIEW CONFIGURATION ------------------------------------------------------------------------------
            dialogEditText = viewSpinner.findViewById(R.id.txt_task_name);
            dialogSpinner = viewSpinner.findViewById(R.id.project_spinner);

// SPINNER -----------------------------------------------------------------------------------------
            populateDialogSpinner();

// CREATE DIALOG -----------------------------------------------------------------------------------
            dialog = alertBuilder.create();

// This instead of listener to positive button in order to avoid automatic dismiss -----------------
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });
            return dialog;
    }

/**
     * Called when the user clicks on the positive button of the SET CALLBACK. _____________________
     *
     * @param dialogInterface the current displayed dialog
     */

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // Get the name of the task
        String taskName = dialogEditText.getText().toString();

        // Get the selected project to be associated to the task
        Project taskProject = null;
        if (dialogSpinner.getSelectedItem() instanceof Project) {
            taskProject = (Project) dialogSpinner.getSelectedItem();
        }

        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                interface_alertDialog.data_Callback_AlertDialog_To_Add_A_Task(taskName,taskProject);
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
 SETTER ____________________________________________________________________________________________
 */

    public void set_Project(Project [] allProjects){
        this.allProjects = allProjects;
    }

/**
 SPINNER CONFIGURATION _____________________________________________________________________________
*/

    public void populateDialogSpinner() {

        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

/**
 SET DATA IN PARAMETER INTERFACE CALLBACK __________________________________________________________
 */

    public interface Interface_AlertDialog {
        void data_Callback_AlertDialog_To_Add_A_Task(String taskName, Project taskProject);
    }

}
