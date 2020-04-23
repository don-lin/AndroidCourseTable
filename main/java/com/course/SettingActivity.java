package com.course;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button save=findViewById(R.id.back);

        Spinner backgroundColor =findViewById(R.id.bgColor);
        Spinner fontColor =findViewById(R.id.fontColor);
        Spinner fontStyle =findViewById(R.id.fontStyle);

        backgroundColor.setSelection(SettingManager.get(getApplicationContext(),"bgc"));
        fontColor.setSelection(SettingManager.get(getApplicationContext(),"fc"));
        fontStyle.setSelection(SettingManager.get(getApplicationContext(),"fs"));

        SettingManager.setBackgroundColor(getApplicationContext(),backgroundColor.getSelectedItemPosition());
        SettingManager.setFontColor(getApplicationContext(),fontColor.getSelectedItemPosition());
        SettingManager.setFontStyle(getApplicationContext(),fontStyle.getSelectedItemPosition());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner backgroundColor =findViewById(R.id.bgColor);
                Spinner fontColor =findViewById(R.id.fontColor);
                Spinner fontStyle =findViewById(R.id.fontStyle);
                SettingManager.setBackgroundColor(getApplicationContext(),backgroundColor.getSelectedItemPosition());
                SettingManager.setFontColor(getApplicationContext(),fontColor.getSelectedItemPosition());
                SettingManager.setFontStyle(getApplicationContext(),fontStyle.getSelectedItemPosition());
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.bg_lecture)
                        .setContentTitle("setting finish")
                        .setContentText("All settings  preserved");

                //设置点击通知之后的响应，启动SettingActivity类
                Intent resultIntent = new Intent(SettingActivity.this,MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                Notification notification = builder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL ;

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1,notification);
            }
        });
    }
}
