package com.wzq.coupleservicedemo;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!checkRunningService("com.wzq.coupleservicedemo:localProcess"))
            startService(new Intent(this, LocalService.class));
        if(!checkRunningService("com.wzq.coupleservicedemo:remoteProcess"))
            startService(new Intent(this, RemoteService.class));
    }



    private boolean checkRunningService(String serviceName)
    {
        ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> serviceInfos = am.getRunningAppProcesses();
        if(serviceInfos != null )
        {
            for(ActivityManager.RunningAppProcessInfo ser:serviceInfos)
            {
                if(ser.processName.equals(serviceName))
                    return true;
            }
        }
        return false;

    }
}
