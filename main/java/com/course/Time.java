package com.course;

public class Time {
    public int hour;
    public int minute;

    public Time(){
        this(0,0);
    }

    public Time(int hour,int minute){
        this.hour=hour;
        this.minute=minute;
    }

    public int convertToMinute(){
        return hour*60+minute;
    }

    public Time convertFromMinute(int minute){
        Time result=new Time(minute/60,minute%60);
        return  result;
    }

    public int diff(Time t){
        return t.convertToMinute()-this.convertToMinute();
    }

    public int getMinuteFromCourse(Course course){
        switch (course.start){
            case 1:return new Time(8,0).convertToMinute();
            case 2:return new Time(9,55).convertToMinute();
            case 3:return new Time(13,30).convertToMinute();
            case 4:return new Time(15,05).convertToMinute();
            case 5:return new Time(18,0).convertToMinute();
        }
        return 0;
    }
}
