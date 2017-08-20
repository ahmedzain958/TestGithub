package com.zain.zainco.serviceexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean mBounded;
    Server mServer;
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                text.setText(mServer.getTime() + " random:" + mServer.getRandom());

                Intent intent =
                        new Intent(Intent.ACTION_DIAL, Uri.parse("tel:555-2368"));
                startActivity(intent);
            }
        });

    }

    ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName name) {
            mBounded = false;
            mServer = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            mBounded = true;
            Server.LocalBinder mLocalBinder = (Server.LocalBinder) service;
            mServer = mLocalBinder.getServerInstance();
        }

    };

    @Override
    protected void onStop() {
        super.onStop();
        if (mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }

    @Override
    protected void onResume() {

        Log.d("activity", "onResume");
        if (mServer == null) {
            Intent intent = new Intent(this, Server.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        super.onResume();
    }


}
