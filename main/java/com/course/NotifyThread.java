package com.course;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.LinkedList;

public class NotifyThread extends Thread {
    public Context context;

    public NotifyThread(Context context){
        this.context=context;
    }

    public void run(){

        while(true) {
            Calendar cal = Calendar.getInstance();
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
            weekDay--;
            if(weekDay==0)
                weekDay=7;
                    ;
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            Time currentTime = new Time(hour, minute);
            Log.d("current_weekday",""+weekDay);
            Log.d("current_hour",""+hour);
            Log.d("current_minute",""+minute);
            DataManager dm = new DataManager(context);
            LinkedList<Course> courses = dm.getAllCourses();
            for (Course course : courses) {
                if (course.weekDay == weekDay) {
                    Log.d("course_start",""+course.start);
                    int diffTime = currentTime.getMinuteFromCourse(course) - currentTime.convertToMinute();
                    Log.d("diffTime",""+diffTime);
                    if (diffTime > 0 && diffTime < course.notification) {

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.bg_lecture)
                                .setContentTitle("course will start in "+ diffTime+" minutes")
                                .setContentText(course.toDetailString());

                        //设置点击通知之后的响应，启动SettingActivity类
                        Intent resultIntent = new Intent(context, MainActivity.class);

                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);

                        Notification notification = builder.build();
                        notification.flags = Notification.FLAG_AUTO_CANCEL;

                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notification);
                        return;
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendNotify(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bg_lecture)
                .setContentTitle("title")
                .setContentText("body");

        Intent resultIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
