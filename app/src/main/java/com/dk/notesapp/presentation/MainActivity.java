package com.dk.notesapp.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import com.dk.notesapp.R;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver internetStatusChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetStatusChangeReceiver = InternetStatusChangeReceiver.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcastReceiver(internetStatusChangeReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(internetStatusChangeReceiver);
    }

    private void registerBroadcastReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, intentFilter);
    }
}