package com.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {
    private int resourceId;
    //initialize the course adapter with the course arraylist
    public CourseAdapter(Context context, int textViewResourceId,
                         List<Course> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    //generate the view of the course adapter
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }

        ImageView courseImage = (ImageView) view.findViewById(R.id.course_img);

        CourseClickListener clickListener=new CourseClickListener(course.weekDay,course.start);
        courseImage.setOnClickListener(clickListener);

        TextView courseCode=(TextView)view.findViewById(R.id.course_code);
        TextView courseName = (TextView) view.findViewById(R.id.course_name);
        TextView coursePosition = (TextView) view.findViewById(R.id.course_position);
        TextView courseTime = (TextView)view.findViewById(R.id.course_time);
        courseImage.setImageResource(course.isLecture?R.drawable.bg_lecture:R.drawable.bg_practice);

        courseTime.setText("weekday : "+course.weekDay+" start : "+course.start+" end : "+course.end);

        courseCode.setText(course.code);
        courseName.setText(course.name);
        coursePosition.setText(course.location);
        return view;
    }
}