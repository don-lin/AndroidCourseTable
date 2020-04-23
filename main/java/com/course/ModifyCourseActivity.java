package com.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class ModifyCourseActivity extends AppCompatActivity {
    public static final String[] StartTime={"8:00","9:55","13:30","15:25","18:00"};
    public static final String[] EndTime={"9:30","11:30","15:05","17:00","20:30"};

    public static Course course;

    private void  init(){
        if(course==null)
            return;
        EditText name=findViewById(R.id.courseName);
        name.setText(course.name);
        EditText courseId=findViewById(R.id.courseId);
        courseId.setText(course.code);
        ToggleButton isLecture=findViewById(R.id.isLecture);
        isLecture.setChecked(course.isLecture);
        Spinner weekDay=findViewById(R.id.weekDay);
        weekDay.setSelection(course.weekDay-1);
        SeekBar start=findViewById(R.id.start_bar);
        start.setProgress(course.start);
        SeekBar end=findViewById(R.id.end_bar);
        end.setProgress(course.end);

        EditText location=findViewById(R.id.location);
        location.setText(course.location);
        EditText comments=findViewById(R.id.comments);
        comments.setText(course.comments);

        SeekBar notification=findViewById(R.id.notification);
        notification.setProgress(course.notification);
        updateNotificationBar(course.notification);

        Button add=findViewById(R.id.add);
        add.setText("save");
        Button cancel=findViewById(R.id.cancel);
        cancel.setText("delete");


    }

    public void updateNotificationBar(int i){
        TextView textView=findViewById(R.id.remind);
        if(i!=0)
            textView.setText("remind me before "+ i+" minutes");
        else
            textView.setText("don't remind me");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_course);

        init();

        final SeekBar start=findViewById(R.id.start_bar);
        final SeekBar end=findViewById(R.id.end_bar);
        Button add=findViewById(R.id.add);
        Button cancel=findViewById(R.id.cancel);

        start.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView startTime=findViewById(R.id.start_text);
                if(i==0)
                    startTime.setText("start time");
                else
                    startTime.setText("start time "+StartTime[i-1]);
                if(end.getProgress()<i)
                    end.setProgress(i);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        end.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView startTime=findViewById(R.id.end_text);
                if(i==0)
                    startTime.setText("start time");
                else
                    startTime.setText("start time "+EndTime[i-1]);
                if(start.getProgress()>i)
                    start.setProgress(i);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        SeekBar notification=findViewById(R.id.notification);
        notification.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateNotificationBar(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences reader=getSharedPreferences("timetable",MODE_PRIVATE);
                String currentString=reader.getString("timetable","0;");
                DataManager dm=new DataManager(getApplicationContext());


                EditText name=findViewById(R.id.courseName);
                EditText courseId=findViewById(R.id.courseId);
                ToggleButton isLecture=findViewById(R.id.isLecture);
                Spinner weekDay=findViewById(R.id.weekDay);
                SeekBar start=findViewById(R.id.start_bar);
                SeekBar end=findViewById(R.id.end_bar);
                SeekBar notification=findViewById(R.id.notification);

                EditText location=findViewById(R.id.location);
                EditText comments=findViewById(R.id.comments);

                LinkedList<Course> courses=dm.getAllCourses(currentString);

                Course c=new Course();

                c.setName(name.getText().toString());
                c.setCode(courseId.getText().toString());

                c.isLecture=isLecture.isChecked();
                c.weekDay=weekDay.getSelectedItemPosition()+1;
                c.start=start.getProgress();
                c.end=end.getProgress();
                c.notification=notification.getProgress();
                Log.d("add1", ""+weekDay.getSelectedItemPosition());
                Log.d("add2", ""+start.getProgress());
                Log.d("add2", ""+end.getProgress());
//                c.setLecture(isLecture.getText().toString());
//                c.setWeekDay(weekDay.getText().toString());
//                c.setStart(start.getText().toString());
//                c.setEnd(end.getText().toString());
                c.setLocation(location.getText().toString());
                c.setComments(comments.getText().toString());

                if(!dm.checkValid(c,courses)) {
                    Toast.makeText(getApplicationContext(),"the time is incorrect, please choose another time",Toast.LENGTH_LONG).show();
                    return;
                }

                courses.add(c);

                currentString = dm.getString(courses);

                Log.d("current string",currentString);

                SharedPreferences.Editor editor=getSharedPreferences("timetable",MODE_PRIVATE).edit();
                editor.putString("timetable",currentString);
                editor.commit();
                Intent intent=new Intent(ModifyCourseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ModifyCourseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
