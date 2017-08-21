package com.zain.zainco.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Zain on 19/08/2017.
 */

public class Server extends Service {
    IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public Server getServerInstance() {
            return Server.this;
        }
    }

    public String getTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    public String getRandom() {
        Random rand = new Random();
        return String.valueOf(rand.nextInt());
    }




    //////////////////////////////////////////////////
}
