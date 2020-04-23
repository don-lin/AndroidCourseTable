package com.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent service = new Intent(MainActivity.this,NotifyService.class);
        startService(service);

        LinearLayout courseTableLinear=findViewById(R.id.timetable);
        courseTableLinear.setBackgroundColor(SettingManager.getBackgroundColorSetting(getApplicationContext()));

        DataManager dataManager=new DataManager(getApplicationContext());
        LinkedList<Course> courses=dataManager.getAllCourses();

        for(int j=1;j<courseTableLinear.getChildCount();j++) {

            LinearLayout linearLayout = (LinearLayout)courseTableLinear.getChildAt(j);

            for (int i = 1; i < linearLayout.getChildCount(); i++) {
                TextView t = (TextView) linearLayout.getChildAt(i);
                t.setText("");
                //t.setText("day "+j+" time " + i);

                Course course = dataManager.getCourse(j,i,courses);


                if(course==null)
                    t.setBackgroundResource(R.drawable.bg_empty);
                else {
                    if(course.isLecture)
                        t.setBackgroundResource(R.drawable.bg_lecture);
                    else t.setBackgroundResource(R.drawable.bg_practice);
                    t.setText(course.toShortString());
                    t.setTypeface(SettingManager.getFontStyleSetting(getApplicationContext()));
                    t.setTextColor(SettingManager.getFontColorSetting(getApplicationContext()));
                    t.setOnClickListener(new CourseClickListener(j,i));
                }
            }
        }

        Button add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ModifyCourseActivity.class);
                ModifyCourseActivity.course=null;
                startActivity(intent);
                finish();
            }
        });

        Button settings=findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button listViewActivity=findViewById(R.id.listview);
        listViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button addByDialog=findViewById(R.id.add_d);
        addByDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                MyDialog myDialog = new MyDialog(MainActivity.this);
                myDialog.setTitle("add a new Course");
                myDialog.show();
            }
        });

        Bitmap b=getViewBitmap(R.layout.activity_main);
        Log.d("drawablea",b.toString());

        BitmapDrawable d=new BitmapDrawable(b);
        settings.setBackground(d);
    }

    public Bitmap getViewBitmap(int layoutId) {

        View view = getLayoutInflater().inflate(layoutId, null);



        LinearLayout courseTableLinear=view.findViewById(R.id.timetable);
        courseTableLinear.setBackgroundColor(SettingManager.getBackgroundColorSetting(getApplicationContext()));

        DataManager dataManager=new DataManager(getApplicationContext());
        LinkedList<Course> courses=dataManager.getAllCourses();

        for(int j=1;j<courseTableLinear.getChildCount();j++) {

            LinearLayout linearLayout = (LinearLayout)courseTableLinear.getChildAt(j);

            for (int i = 1; i < linearLayout.getChildCount(); i++) {
                TextView t = (TextView) linearLayout.getChildAt(i);
                t.setText("");

                Course course = dataManager.getCourse(j,i,courses);


                if(course!=null){
                    t.setText(course.toShortString());
                    t.setTypeface(SettingManager.getFontStyleSetting(getApplicationContext()));
                    t.setTextColor(SettingManager.getFontColorSetting(getApplicationContext()));
                }
            }
        }

        int me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(me,me);
        view.layout(0 ,0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }
}
