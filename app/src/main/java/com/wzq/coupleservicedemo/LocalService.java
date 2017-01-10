package com.wzq.coupleservicedemo;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by wzq on 17-1-10.
 */

public class LocalService extends IntentService {
    private LocalBinder binder;
    @Override
    public void onCreate() {
        super.onCreate();
        binder = new LocalBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */




    public LocalService(String name) {
        super(name);
    }

    public LocalService()
    {
        this("LocalService");
    }
//

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this, RemoteService.class), conn, BIND_AUTO_CREATE);
        Toast.makeText(LocalService.this, "onStartCommand LocalService", Toast.LENGTH_SHORT).show();
        try {
            binder.print();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(LocalService.this, RemoteService.class));
            bindService(new Intent(LocalService.this, RemoteService.class), conn, BIND_AUTO_CREATE);
        }
    };

    class LocalBinder extends LocalAidl.Stub{

        @Override
        public void print() throws RemoteException {
            Toast.makeText(LocalService.this, "这是LocalService", Toast.LENGTH_SHORT).show();
        }
    }

}
