package com.example.asus.notififcations;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.asus.notififcations.adapter.CurrentTaskAdapter;
import com.example.asus.notififcations.adapter.TabAdapter;
import com.example.asus.notififcations.database.DBHelper;
import com.example.asus.notififcations.dialog.AddingTaskDialog;

import com.example.asus.notififcations.fragment.CurrentTaskFragment;
import com.example.asus.notififcations.fragment.DoneTaskFragment;
import com.example.asus.notififcations.fragment.TaskFragment;
import com.example.asus.notififcations.model.ModelTask;

public class MainActivity extends AppCompatActivity implements AddingTaskDialog.AddingTaskListener, CurrentTaskFragment.OnTaskDoneListener, DoneTaskFragment.OnTaskRestoreListener {
    FragmentManager fragmentManager;
    TabAdapter tabAdapter;
    TaskFragment currentTaskFragment;
    TaskFragment doneTaskFragment;

    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getApplicationContext());
        fragmentManager=getFragmentManager();


        setUI();
    }


    private void setUI(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.tollbar);
        if(toolbar!=null){
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager= (ViewPager) findViewById(R.id.pager);
        tabAdapter=new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        currentTaskFragment= (CurrentTaskFragment) tabAdapter.getItem(TabAdapter.CURREN_TASK_POSITION);
        doneTaskFragment= (DoneTaskFragment) tabAdapter.getItem(TabAdapter.DONE_TASK_POSITION);


        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialog=new AddingTaskDialog();
                addingTaskDialog.show(fragmentManager,"AddingTaskDialog");

            }
        });


    }

    @Override
    public void OnTaskAdded(ModelTask newTask) {
            currentTaskFragment.addTask(newTask, true);
    }

    @Override
    public void OnTaskCancel() {
        Toast.makeText(this,"Task canceled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskDone(ModelTask task) {
        doneTaskFragment.addTask(task, false);
    }

    @Override
    public void OnTaskRestore(ModelTask task) {
        currentTaskFragment.addTask(task, false);
    }
}
