package com.dk.notesapp;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dk.notesapp.database.LocalRepositoryImpl;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.DataProviderObserver;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizer;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizerObserver;
import com.dk.notesapp.utils.NotesSynchronizer.SyncNotesService;
import com.dk.notesapp.utils.NotesSynchronizer.SyncStage;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataProvider, NotesSynchronizer {
    private BroadcastReceiver internetStatusChangeReceiver;
    private LocalRepositoryImpl localRepository;
    private List<DataProviderObserver> dataProviderObservers;
    private List<NotesSynchronizerObserver> notesSynchronizerObservers;
    private List<Note> notes;
    private SyncStage currentSyncStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetStatusChangeReceiver = InternetStatusChangeReceiver.getInstance();
        localRepository = new LocalRepositoryImpl();
        dataProviderObservers = new LinkedList<>();
        notesSynchronizerObservers = new LinkedList<>();
        currentSyncStage = SyncStage.NOT_STARTED;
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.popBackStack(R.id.listFragment, false);
    }

    @Override
    public void startSync() {
        ResultReceiver receiver = new SyncStatusReceiver(new Handler());
        startService(SyncNotesService.getIntent(this, receiver));
    }

    @Override
    public void registerObserver(NotesSynchronizerObserver observer) {
        notesSynchronizerObservers.add(observer);
        observer.onUpdateSyncStatus(currentSyncStage);
    }

    @Override
    public void removeObserver(NotesSynchronizerObserver observer) {
        notesSynchronizerObservers.remove(observer);
    }

    @Override
    public void notifyObservers(SyncStage syncStage) {
        for (NotesSynchronizerObserver observer: notesSynchronizerObservers){
            observer.onUpdateSyncStatus(syncStage);
        }
    }

    @Override
    public void deleteNote(Note note) {
        localRepository.delete(note);
    }

    @Override
    public void addNewNote() {
        localRepository.insertOrUpdate(new Note());
    }

    @Override
    public void updateNote(Note note) {
        localRepository.insertOrUpdate(note);
    }

    @Override
    public void registerObserver(DataProviderObserver observer) {
        dataProviderObservers.add(observer);
        if (notes != null) {
            observer.onNotesListUpdate(notes);
        }
    }

    @Override
    public void removeObserver(DataProviderObserver observer) {
        dataProviderObservers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (DataProviderObserver observer: dataProviderObservers) {
            observer.onNotesListUpdate(notes);
        }
    }

    @Override
    public void startLoadData() {
        LiveData<List<Note>> notesLiveData = localRepository.getAll();
        notesLiveData.observe(this, observer);
    }

    private Observer<List<Note>> observer = new Observer<List<Note>>() {
        @Override
        public void onChanged(List<Note> notes) {
            setNotes(notes);
        }
    };

    private void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyObservers();
    }

    private void registerBroadcastReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, intentFilter);
    }

    private class SyncStatusReceiver extends ResultReceiver {
        public SyncStatusReceiver(Handler handler) {
            super(handler);
        }

        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            currentSyncStage = (SyncStage) resultData.getSerializable(getString(R.string.stage_key));
            notifyObservers(currentSyncStage);
        }
    }
}