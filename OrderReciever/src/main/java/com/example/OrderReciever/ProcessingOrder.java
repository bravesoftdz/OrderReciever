package com.example.OrderReciever;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class ProcessingOrder extends Activity {

    boolean mBounded;
    ClientService mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_order);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.processing_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent service = new Intent(this, ClientService.class);
        bindService(service, mConnection, BIND_AUTO_CREATE);


    }

    public void onClickSend(View v) {
        Bundle extras = getIntent().getExtras();
        String unique = extras.getString("UNIQUE_ID");

        mServer.onClickSend(String.format("NOTIFY#%s/", unique));
    }

    ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getBaseContext(), "Service is disconnected", Toast.LENGTH_LONG).show();
            mBounded = false;
            mServer = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getBaseContext(), "Service is connected", Toast.LENGTH_LONG).show();
            mBounded = true;
            ClientService.LocalBinder mLocalBinder = (ClientService.LocalBinder) service;
            mServer = mLocalBinder.getClientServiceInstance();
        }
    };
}
