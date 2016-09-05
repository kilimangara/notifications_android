package com.example.asus.notififcations.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.asus.notififcations.R;
import com.example.asus.notififcations.Utils;

import java.util.Calendar;

import com.example.asus.notififcations.alarm.AlarmHelper;
import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 25.08.2016.
 */
public class AddingTaskDialog extends DialogFragment {
    private AddingTaskListener addingTaskListener;

    public interface AddingTaskListener{
        void OnTaskAdded(ModelTask modelTask);
        void OnTaskCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
                addingTaskListener = (AddingTaskListener) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement addingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder= new  AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();

        builder.setTitle(R.string.dialog_title);


        View container =inflater.inflate(R.layout.dialog_task, null);
        final TextInputLayout tilTitle= (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        final EditText edTitle= tilTitle.getEditText();

        final TextInputLayout tilDate= (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        final EditText edDate= tilDate.getEditText();

        final TextInputLayout tilTime= (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        final EditText edTime= tilTime.getEditText();
        Spinner spPriority = (Spinner) container.findViewById(R.id.spDialogTaskPriority);



        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilDate.setHint(getResources().getString(R.string.task_date));
        tilTime.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        final ModelTask modelTask=new ModelTask();

        ArrayAdapter<String> priorityAdapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ModelTask.PRIORITY_LEVELS);
        spPriority.setAdapter(priorityAdapter);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelTask.setPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+1);

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edDate.length() == 0){
                    edDate.setText(" ");
                }
                DialogFragment datepickerfragment=new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        edDate.setText(null);
                    }
                };
                datepickerfragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTime.length() == 0){
                    edTime.setText(" ");
                }
                DialogFragment timepickerfragment=new TimePickerFragment(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        edTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        edTime.setText(null);
                    }
                };
                timepickerfragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modelTask.setTitle(edTitle.getText().toString());
                modelTask.setStatus(ModelTask.STATUS_CURRENT);
                if(edTime.length()!=0 || edTitle.length()!=0){
                    modelTask.setDate(calendar.getTimeInMillis());

                    AlarmHelper alarmHelper = AlarmHelper.getInstance();
                    alarmHelper.setAlarm(modelTask);
                }
                modelTask.setStatus(ModelTask.STATUS_CURRENT);
                addingTaskListener.OnTaskAdded(modelTask);
                dialog.dismiss();
            }
        });


        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingTaskListener.OnTaskCancel();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positivebutton= ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if(edTitle.length() == 0){
                    positivebutton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                edTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            positivebutton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                        }
                        else{
                            positivebutton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            }
        });


        return alertDialog;
    }
}

