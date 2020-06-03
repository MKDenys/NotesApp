package com.dk.notesapp.presentation;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.dk.notesapp.R;
import com.dk.notesapp.database.LocalRepository;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.DataProviderObserver;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;
import com.dk.notesapp.utils.NotesSynchronizer;
import com.dk.notesapp.utils.SyncNotesService;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataProvider, NotesSynchronizer {
    private BroadcastReceiver internetStatusChangeReceiver;
    private LocalRepository localRepository;
    private LiveData<List<Note>> localNotesLiveData;
    private List<DataProviderObserver> observers;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetStatusChangeReceiver = InternetStatusChangeReceiver.getInstance();
        localRepository = new LocalRepository();
        observers = new LinkedList<>();
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

    @Override
    public void startLoadData() {
        localNotesLiveData = localRepository.getAll();
        localNotesLiveData.observe(this, observer);
    }

    @Override
    public void registerObserver(DataProviderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(DataProviderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (DataProviderObserver observer: observers) {
            observer.onUpdate(notes);
        }
    }

    private Observer<List<Note>> observer = new Observer<List<Note>>() {
        @Override
        public void onChanged(List<Note> notes) {
            MainActivity.this.notes = notes;
            notifyObservers();
        }
    };

    @Override
    public void startSync() {
        startService(SyncNotesService.getIntent(this));
    }
}