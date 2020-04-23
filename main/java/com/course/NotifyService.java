package com.course;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NotifyService extends Service {
    public NotifyThread nt;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service_notify","start");
        nt=new NotifyThread(getApplicationContext());
        nt.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        nt.stop();
    }

}