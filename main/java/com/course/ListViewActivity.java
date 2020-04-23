package com.course;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        DataManager dataManager=new DataManager(getApplicationContext());
        LinkedList<Course> courses=dataManager.getAllCourses();



        CourseAdapter adapter = new CourseAdapter(ListViewActivity.this,R.layout.course_item,courses);
        ListView listView = (ListView) findViewById(R.id.courseListView);
        listView.setAdapter(adapter);
    }
}
