package com.course;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.LinkedList;

public class CourseClickListener implements View.OnClickListener{
    private int weekDay;
    private int time;

    public CourseClickListener(int weekDay,int time){
        this.weekDay=weekDay;
        this.time=time;
    }
    @Override
    public void onClick(View view) {
        Context context=view.getContext();
        DataManager dm=new DataManager(context);
        LinkedList<Course> list=dm.getAllCourses();
        Course course = dm.getCourse(weekDay,time,list);
        if(course==null)
            return ;
        ModifyCourseActivity.course=course;
        dm.deleteCourse(course,list);
        dm.saveCourses(list);
        Intent intent=new Intent(context, ModifyCourseActivity.class);
        context.startActivity(intent);
    }

}
