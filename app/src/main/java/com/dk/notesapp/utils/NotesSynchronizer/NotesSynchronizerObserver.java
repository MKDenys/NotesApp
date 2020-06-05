package com.dk.notesapp.utils.NotesSynchronizer;

public interface NotesSynchronizerObserver {
    void onUpdateSyncStatus(SyncStage syncStage);
}
