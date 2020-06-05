package com.dk.notesapp.utils.NotesSynchronizer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.Nullable;

import com.dk.notesapp.R;
import com.dk.notesapp.database.LocalRepositoryImpl;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.model.RemoteRepository;

import java.util.List;

public class SyncNotesService extends IntentService {
    private static final String TAG = "SyncNotesService";
    private static final String RECEIVER_KEY = "receiver";
    private static final int RESULT_CODE = 1;
    private RemoteRepository remoteRepository;
    private LocalRepositoryImpl localRepository;

    public static Intent getIntent(Context context, ResultReceiver receiver){
        Intent intent = new Intent(context, SyncNotesService.class);
        intent.putExtra(RECEIVER_KEY, receiver);
        return intent;
    }

    public SyncNotesService() {
        super(TAG);
        remoteRepository = new RemoteRepository();
        localRepository = new LocalRepositoryImpl();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER_KEY);
        assert receiver != null;
        setCurrentStage(SyncStage.STARTED, receiver);
        try {
            List<Note> newNotes = remoteRepository.getAll();
            if (newNotes.isEmpty()){
                setCurrentStage(SyncStage.COMPLETE_WITH_EMPTY_LIST, receiver);
                return;
            }
            localRepository.insertOrUpdate(newNotes);
            setCurrentStage(SyncStage.COMPLETE_SUCCESSFULLY, receiver);
        } catch (Exception ex) {
            setCurrentStage(SyncStage.COMPLETE_WITH_ERROR, receiver);
        }
    }

    private void setCurrentStage(SyncStage currentStage, ResultReceiver receiver){
        Bundle data = new Bundle();
        data.putSerializable(getString(R.string.stage_key), currentStage);
        receiver.send(RESULT_CODE, data);
    }
}
