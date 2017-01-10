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

public class RemoteService extends IntentService {




    private RemoteBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class), conn, BIND_AUTO_CREATE);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new RemoteBinder();
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    public RemoteService(String name) {
        super(name);
    }

    public RemoteService() {
        this("RemoteService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(RemoteService.this, LocalService.class), conn, BIND_AUTO_CREATE);
        Toast.makeText(RemoteService.this, "onStartCommand RemoteService", Toast.LENGTH_SHORT).show();
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    class RemoteBinder extends RemoteAidl.Stub{

        @Override
        public void print() throws RemoteException {
            Toast.makeText(RemoteService.this, "这是RemoteService", Toast.LENGTH_SHORT).show();
        }
    }
}
