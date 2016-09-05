package com.example.asus.notififcations.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.asus.notififcations.MainActivity;
import com.example.asus.notififcations.R;
import com.example.asus.notififcations.adapter.CurrentTaskAdapter;
import com.example.asus.notififcations.adapter.TaskAdapter;
import com.example.asus.notififcations.alarm.AlarmHelper;
import com.example.asus.notififcations.model.Item;
import com.example.asus.notififcations.model.ModelTask;

/**
 * Created by Asus on 30.08.2016.
 */
public abstract class TaskFragment extends Fragment {
    protected RecyclerView recycleView;
    protected RecyclerView.LayoutManager layoutManager;

    protected TaskAdapter adapter;

    public MainActivity activity;
    public AlarmHelper alarmHelper;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null){
            activity = (MainActivity) getActivity();
        }
        alarmHelper=AlarmHelper.getInstance();
        addTaskFromDb();
    }

    public void addTask(ModelTask newTask, boolean saveToDb){
        int position= -1;

        for(int i=0;i< adapter.getItemCount();i++) {
            if (adapter.getItem(i).isTask()) {
                ModelTask task = (ModelTask) adapter.getItem(i);
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;
                }
            }
        }

        if (position != -1) {
            adapter.addItem(position, newTask);
        }else{
            adapter.addItem(newTask);
        }

        if(saveToDb){
            activity.dbHelper.saveTask(newTask);
        }
    }

    public void removeTaskDialog(final int location){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
        alertdialog.setMessage(R.string.dialog_removing_task);

        Item item = adapter.getItem(location);

        if(item.isTask()){
            ModelTask removingTask = (ModelTask) item;

            final long timeStamp = removingTask.getTime_stamp();
            final boolean[] isRemoved = {false};
            alertdialog.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.removeItem(location);
                    isRemoved[0] = true;
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator),
                            R.string.removed, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          addTask(activity.dbHelper.query().getTask(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });
                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            if(isRemoved[0]){
                                alarmHelper.removeAlarm(timeStamp);
                                activity.dbHelper.removeTask(timeStamp);

                            }
                        }
                    });
                    snackbar.show();

                    dialog.dismiss();
                }
            });

            alertdialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertdialog.show();
        }



    }
    public abstract  void findTasks(String title);

    public abstract void addTaskFromDb();

    public abstract void moveTask(ModelTask newTask);

}
