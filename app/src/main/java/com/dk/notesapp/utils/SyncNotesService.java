package com.dk.notesapp.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.dk.notesapp.database.LocalRepository;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.network.RemoteRepository;

import java.util.List;

public class SyncNotesService extends IntentService{
    private static final String TAG = "SyncNotesService";
    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;

    public static Intent getIntent(Context context){
        return new Intent(context, SyncNotesService.class);
    }

    public SyncNotesService() {
        super(TAG);
        remoteRepository = new RemoteRepository();
        localRepository = new LocalRepository();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        List<Note> newNotes = remoteRepository.getAll();
        localRepository.insertOrUpdate(newNotes);
    }
}
