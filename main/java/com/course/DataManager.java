package com.course;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.LinkedList;

public class DataManager {
    public Context context;

    public DataManager(Context context){
        this.context=context;
    }

    public LinkedList<Course> getAllCourses(){
        return getAllCourses(getFileString());
    }

    public void saveCourses(LinkedList<Course> courses){
        setFileString(getString(courses));
    }

    public void setFileString(String s){
        SharedPreferences.Editor editor=context.getSharedPreferences("timetable",context.MODE_PRIVATE).edit();
        editor.putString("timetable",s);
        editor.commit();
    }
    public String getIntroductionFileString(LinkedList<Course> courses){
        String result="";
        for(Course course : courses){
            result=result+course.toDetailString()+"\n";
        }
        return result;
    }

    public String getFileString(){
        SharedPreferences reader=context.getSharedPreferences("timetable",context.MODE_PRIVATE);
        String currentString=reader.getString("timetable","0;");
        Log.d("get_file_string",currentString);
        return currentString;
    }

    public void deleteCourse(Course course,LinkedList<Course> courses){
        for(int i=course.start;i<=course.end;i++)
        deleteCourse(course.weekDay,i,courses);
    }

    public void deleteCourse(int weekDay,int time,LinkedList<Course> courses){
        for(Course c : courses){
            if(c.weekDay==weekDay&&c.start<=time&&c.end>=time) {
                courses.remove(c);
                return;
            }
        }
    }


    public boolean checkValid(Course course,LinkedList<Course> courses){
        return checkValid(course.weekDay,course.start,course.end,courses);
    }

    /**
     *
     * @param weekDay the week day
     * @param startTime the start time that should be checked
     * @param endTime the end time that should be checked
     * @param courses the courses list
     * @return true if the time is value, false if the time is not valid
     */
    public boolean checkValid(int weekDay,int startTime,int endTime,LinkedList<Course> courses){
        if(weekDay<=0||weekDay>6)
            return false;
        if(startTime<=0)
            return false;
        if(endTime<=0)
            return false;
        for(int i=startTime;i<=endTime;i++){
            if(getCourse(weekDay,i,courses)!=null) {
                Log.d("errorc",getCourse(weekDay,i,courses).toString());
                return false;
            }
        }
        return true;
    }

    public Course getCourse(int weekDay,int time,LinkedList<Course> courses){
        for(Course c : courses){
            if(c.weekDay==weekDay&&c.start<=time&&c.end>=time)
                return c;
        }
        return null;
    }

    public LinkedList<Course> getAllCourses(String s){
        Log.d("all_courses",s);
        String[] arr=s.split(";");
        int size=Integer.parseInt(arr[0]);
        LinkedList<Course> result=new LinkedList<Course>();
        for(int i=0;i<size;i++){
            Course c=new Course();
            int startIndex=i*9+1;
            c.setName(arr[startIndex]);
            c.setCode(arr[startIndex+1]);
            c.setLecture(arr[startIndex+2]);
            c.setWeekDay(arr[startIndex+3]);
            c.setStart(arr[startIndex+4]);
            c.setEnd(arr[startIndex+5]);
            c.setLocation(arr[startIndex+6]);
            c.setComments(arr[startIndex+7]);
            c.setNotification(arr[startIndex+8]);


//            c.name=arr[startIndex];
//            c.code=arr[startIndex+1];
//            c.isLecture=arr[startIndex+2].equals("1");
//            c.weekDay=Integer.parseInt(arr[startIndex+3]);
//            c.start=Integer.parseInt(arr[startIndex+4]);
//            c.end=Integer.parseInt(arr[startIndex+5]);
//            c.location=arr[startIndex+6];
//            c.comments=arr[startIndex+7];
            result.add(c);
        }
        return result;
    }

    public String getString(LinkedList<Course> courses){
        String result=Integer.toString(courses.size())+";";
        for(Course c : courses){
            String currentCourse=c.toString();
            result=result+currentCourse;
        }
        return result;
    }
}
