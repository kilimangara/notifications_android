package com.example.asus.notififcations;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.asus.notififcations.adapter.TabAdapter;
import com.example.asus.notififcations.dialog.AddingTaskDialog;

import model.ModelTask;

public class MainActivity extends AppCompatActivity implements AddingTaskDialog.AddingTaskListener {
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        TabAdapter tabAdapter=new TabAdapter(fragmentManager, 2);

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
        Toast.makeText(this,"Task added",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnTaskCancel() {
        Toast.makeText(this,"Task canceled", Toast.LENGTH_LONG).show();
    }
}
